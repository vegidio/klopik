package main

/*
#include <stdlib.h>
*/
import "C"
import (
	"unsafe"
)

func main() {
	//cMethod := C.CString("GET")
	//cUrl := C.CString("https://httpbin.org/get")
	//body, size, httpCode, headers, err := Request(cMethod, cUrl, nil, nil)
	//
	//fmt.Println("Body", body)
	//fmt.Println("Size", size)
	//fmt.Println("HTTP Code", httpCode)
	//fmt.Println("Headers", C.GoString(headers))
	//fmt.Println("Error", C.GoString(err))
}

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
	gMethod := C.GoString(method)
	gUrl := C.GoString(url)
	request := createRequest(body, headers)
	resp, err := request.Execute(gMethod, gUrl)

	// Extracting response
	gBody := resp.Body()
	cBody := C.CBytes(gBody)
	cLength := C.int(len(gBody))
	cHttpCode := C.int(resp.StatusCode())
	cHeaders := getResponseHeaders(resp.Header())

	if err != nil {
		cErr := C.CString(err.Error())
		return cBody, cLength, cHttpCode, cHeaders, cErr
	}

	return cBody, cLength, cHttpCode, cHeaders, nil
}
