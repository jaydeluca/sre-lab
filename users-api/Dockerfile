FROM golang:1.18.5-alpine3.65 as builder

# Enable go modules
ENV GO111MODULE=on

# Install git. (alpine image does not have git in it, needed for gcc)
RUN apk update && apk add --no-cache git

WORKDIR /app

COPY ./users-api/go.mod ./
RUN go mod download

COPY ./users-api/ .

# Note here: CGO_ENABLED is disabled for cross system compilation
# It is also a common best practise.
RUN CGO_ENABLED=0 go build -o ./bin/main .

FROM scratch

# Copy the Pre-built binary file
COPY --from=builder /app/bin/main .

# Run executable
CMD ["./main"]