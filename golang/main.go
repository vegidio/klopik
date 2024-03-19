package main

/*
#include <stdlib.h>
*/
import "C"
import (
	"unsafe"
)

func main() {}

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
func Request(method *C.char, url *C.char, body *C.char, headers *C.char) (unsafe.Pointer, C.int, *C.char) {
	gMethod := C.GoString(method)
	gUrl := C.GoString(url)
	request := createRequest(body, headers)
	resp, err := request.Execute(gMethod, gUrl)

	if err != nil {
		cErr := C.CString(err.Error())
		return nil, 0, cErr
	}

	gBody := resp.Body()
	cBody := C.CBytes(gBody)
	cLength := C.int(len(gBody))
	return cBody, cLength, nil
}
