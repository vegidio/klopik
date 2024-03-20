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

// Request is a function that takes in four parameters: method, url, body, and headers.
// It uses these parameters to create an HTTP request and send it to the specified URL.
//
// Parameters:
// method: a pointer to a C character representing the HTTP method (e.g., GET, POST).
// url: a pointer to a C character representing the URL where the request will be sent.
// body: a pointer to a C character representing the body of the request.
// headers: a pointer to a C character representing the headers of the request.
//
// Returns:
// The function returns three values:
// - a pointer to an unsafe.Pointer representing the body of the response (or nil in case of an error),
// - an integer representing the length of the body of the response (or 0 in case of an error),
// - a pointer to a C character representing the error message (or nil if there is no error).
//
//export Request
func Request(
	method *C.char,
	url *C.char,
	body *C.char,
	headers *C.char) (unsafe.Pointer, C.int, C.int, *C.char, *C.char) {

	gMethod := C.GoString(method)
	gUrl := C.GoString(url)
	request := createRequest(body, headers)
	resp, err := request.Execute(gMethod, gUrl)

	if err != nil {
		cErr := C.CString(err.Error())
		return nil, 0, 0, nil, cErr
	}

	gBody := resp.Body()
	cBody := C.CBytes(gBody)
	cLength := C.int(len(gBody))
	cHttpCode := C.int(resp.StatusCode())
	cHeaders := getResponseHeaders(resp.Header())
	return cBody, cLength, cHttpCode, cHeaders, nil
}
