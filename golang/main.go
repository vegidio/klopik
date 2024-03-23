package main

/*
#include <stdlib.h>
*/
import "C"
import (
	"io"
	"net/http"
	"unsafe"
)

func main() {
	// Request test
	//testRequest()

	// Stream test
	//testStream()
}

var client = &http.Client{}

// Request is a function that sends an HTTP request and returns the response.
//
// It takes four parameters:
// - method: a pointer to a C string representing the HTTP method (e.g., "GET", "POST").
// - url: a pointer to a C string representing the URL to which the request will be sent.
// - body: a pointer to a C string representing the body of the request.
// - headers: a pointer to a C string representing the headers of the request.
//
// The function returns five values:
// - a pointer to the body of the response (as an unsafe.Pointer),
// - the length of the body (as a C integer),
// - the HTTP status code of the response (as a C integer),
// - a pointer to a C string representing the headers of the response,
// - a pointer to a C string representing any error that occurred (or nil if no error occurred).
//
//export Request
func Request(
	method *C.char,
	url *C.char,
	body *C.char,
	headers *C.char) (unsafe.Pointer, C.int, C.int, *C.char, *C.char) {

	// Sending request
	request := createRequest(method, url, body, headers)
	resp, err := client.Do(request)
	if err != nil {
		return nil, 0, 0, nil, C.CString(err.Error())
	}

	defer resp.Body.Close()

	// Extracting response
	gBody, err := io.ReadAll(resp.Body)
	cBody := C.CBytes(gBody)
	cLength := C.int(resp.ContentLength)
	cHttpCode := C.int(resp.StatusCode)
	cHeaders := getResponseHeaders(resp.Header)

	if err != nil {
		cErr := C.CString(err.Error())
		return cBody, cLength, cHttpCode, cHeaders, cErr
	}

	return cBody, cLength, cHttpCode, cHeaders, nil
}

// Stream is a function that sends an HTTP request and streams the response.
//
// It takes five parameters:
// - method: a pointer to a C string representing the HTTP method (e.g., "GET", "POST").
// - url: a pointer to a C string representing the URL to which the request will be sent.
// - body: a pointer to a C string representing the body of the request.
// - headers: a pointer to a C string representing the headers of the request.
// - callback: a pointer to a function that will be called for each chunk of the response body.
//
// The function returns four values:
// - the length of the body (as a C integer),
// - the HTTP status code of the response (as a C integer),
// - a pointer to a C string representing the headers of the response,
// - a pointer to a C string representing any error that occurred (or nil if no error occurred).
//
// The function sends the request, then reads the response body in chunks of 4096 bytes. For each chunk,
// it calls the callback function with the chunk as a parameter. If an error occurs during this process,
// the function immediately returns with the error.
//
//export Stream
func Stream(
	method *C.char,
	url *C.char,
	body *C.char,
	headers *C.char,
	callback unsafe.Pointer) (C.int, C.int, *C.char, *C.char) {

	// Sending request
	request := createRequest(method, url, body, headers)
	resp, err := client.Do(request)
	if err != nil {
		return 0, 0, nil, C.CString(err.Error())
	}

	defer resp.Body.Close()

	gLength := 0
	cHttpCode := C.int(resp.StatusCode)
	cHeaders := getResponseHeaders(resp.Header)

	buffer := make([]byte, 4096)
	cb := *(*func(unsafe.Pointer, C.int))(callback)

	for {
		// Read a chunk of data
		size, err := resp.Body.Read(buffer)
		if err != nil && err != io.EOF {
			cLength := C.int(gLength)
			cErr := C.CString(err.Error())
			return cLength, cHttpCode, cHeaders, cErr
		}

		gLength += size

		// Exit if no more data to read
		if size == 0 {
			break
		}

		// Send the chunk to the callback
		cb(C.CBytes(buffer), C.int(size))
	}

	cLength := C.int(gLength)
	return cLength, cHttpCode, cHeaders, nil
}
