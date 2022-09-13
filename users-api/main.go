package main

import (
	"encoding/json"
	"fmt"
	"log"
	"math/rand"
	"net/http"
	"os"
	"time"
)

func enableCors(w *http.ResponseWriter) {
	(*w).Header().Set("Access-Control-Allow-Origin", "*")
}

type User struct {
	Id   uint   `json:"id"`
	Name string `json:"name"`
}

func respondWithJSON(w http.ResponseWriter, code int, payload interface{}) {
	response, _ := json.Marshal(payload)
	w.Header().Set("Content-Type", "application/json")
	w.WriteHeader(code)
	_, err := w.Write(response)
	if err != nil {
		fmt.Println(err)
		return
	}
}

func getEnv(key, fallback string) string {
	if value, ok := os.LookupEnv(key); ok {
		return value
	}
	return fallback
}

func main() {

	http.HandleFunc("/", func(w http.ResponseWriter, r *http.Request) {
		enableCors(&w)

		rand.Seed(time.Now().UnixNano())
		n := rand.Intn(3) // n will be between 0 and 10
		fmt.Printf("Sleeping %d seconds...\n", n)
		time.Sleep(time.Duration(n)*time.Second)

		user := User{
			Id:   29,
			Name: "test",
		}
		fmt.Println(fmt.Sprintf("User: %v", user))
		respondWithJSON(w, http.StatusOK, user)

	})
	http.HandleFunc("/health", func(w http.ResponseWriter, r *http.Request) {
		enableCors(&w)
		respondWithJSON(w, http.StatusOK, "Success")
	})

	port := getEnv("PORT", "9996")
	portString := fmt.Sprintf(":%v", port)
	fmt.Printf("Starting server on port %v", port)
	log.Fatal(http.ListenAndServe(portString, nil))
}