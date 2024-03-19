package main

/*
#include <stdlib.h>
*/
import "C"

import (
	"encoding/json"
	"fmt"
	"github.com/go-resty/resty/v2"
)

var client = resty.New()

// createRequest is a function that creates a new resty.Request.
// It takes two parameters: body and headers, both of which are pointers to C.char.
// The body and headers are optional. If provided, they are set in the request.
// The function returns a pointer to the created resty.Request.
//
// Parameters:
//   - body: a pointer to a C.char that represents the body of the request. If it's not nil, it will be converted to a
//     Go string and set in the request.
//   - headers: a pointer to a C.char that represents the headers of the request in JSON format. If it's not nil, it
//     will be converted to a Go string, unmarshalled to a map[string]string to be set in the request.
//
// Returns:
//   - a pointer to the created resty.Request.
func createRequest(body *C.char, headers *C.char) *resty.Request {
	r := client.R()

	if body != nil {
		r = r.SetBody(getBody(body))
	}
	if headers != nil {
		r = r.SetHeaders(getHeaders(headers))
	}

	return r
}

func getBody(body *C.char) string {
	return C.GoString(body)
}

func getHeaders(headers *C.char) map[string]string {
	gHeaders := C.GoString(headers)

	result := make(map[string]interface{})
	err := json.Unmarshal([]byte(gHeaders), &result)
	if err != nil {
		return map[string]string{}
	}

	stringMap := make(map[string]string)
	for key, value := range result {
		stringMap[key] = fmt.Sprintf("%v", value)
	}

	return stringMap
}
