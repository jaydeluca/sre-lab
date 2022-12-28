package controllers

import (
	"fmt"
	"github.com/gin-gonic/gin"
	"github.com/jaydeluca/sre-lab/users-api/models"
	"go.opentelemetry.io/otel/attribute"
	"go.opentelemetry.io/otel/trace"
	"math/rand"
	"net/http"
	"time"
)


func FindUsers(c *gin.Context) {
	span := trace.SpanFromContext(c.Request.Context())
	span.SetAttributes(attribute.String("controller", "users"))
	span.AddEvent("Retrieve Users", trace.WithAttributes(attribute.Int("userId", 394)))
	rand.Seed(time.Now().UnixNano())
	n := rand.Intn(3) // n will be between 0 and 10
	fmt.Printf("Sleeping %d seconds...\n", n)
	time.Sleep(time.Duration(n)*time.Second)

	user := models.User{
		Id:   29,
		Name: "test",
	}
	fmt.Println(fmt.Sprintf("User: %v", user))
	c.JSON(http.StatusOK, gin.H{"user": user})
}