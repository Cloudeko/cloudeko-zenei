#!/bin/bash

# Create a directory to store the keys
mkdir -p core/src/main/resources

# Generate a development private key
openssl genpkey -algorithm RSA -out platform/src/main/resources/dev-private-key.pem -pkeyopt rsa_keygen_bits:2048

# Extract the public key from the private key
openssl rsa -in platform/src/main/resources/dev-private-key.pem -pubout -out platform/src/main/resources/dev-public-key.pem