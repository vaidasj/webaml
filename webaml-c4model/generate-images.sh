#!/bin/sh

format=${1:-png}

# Export to DOT
structurizr-cli export -workspace webaml.dsl -format dot -output dot-files

# Convert to image

mkdir -p images

for filename in dot-files/*.dot; do
    dot -T"$format" -Glabel="" "$filename" > "images/$(basename "$filename").$format"
done

rm -rf dot-files
