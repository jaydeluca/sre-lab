package controllers

import (
	"fmt"
	"github.com/gin-gonic/gin"
	"github.com/jaydeluca/sre-lab/users-api/models"
	"go.opentelemetry.io/otel/attribute"
	"go.opentelemetry.io/otel/trace"
	"math/rand"
	"net/http"
	"strconv"
	"time"
)

func FindUsers(c *gin.Context) {
	userId, _ := strconv.Atoi(c.GetHeader("user_id"))

	span := trace.SpanFromContext(c.Request.Context())
	span.SetAttributes(attribute.String("controller", "users"))
	span.SetAttributes(attribute.String("user_id", strconv.Itoa(userId)))
	span.AddEvent("Retrieve Users", trace.WithAttributes(attribute.Int("userId", userId)))

	if userId == 30 {
		rand.Seed(time.Now().UnixNano())
		n := rand.Intn(10) // n will be between 0 and 3
		fmt.Printf("Sleeping %d seconds...\n", n)
		time.Sleep(time.Duration(n) * time.Second)
	}

	user := models.User{
		Id:   uint(userId),
		Name: "test",
	}
	fmt.Println(fmt.Sprintf("User: %v", user))
	c.JSON(http.StatusOK, gin.H{"user": user})
}
