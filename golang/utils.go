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
