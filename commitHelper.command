#!/bin/bash

read -p "Enter commit message: " commit_message

# Check if there are changes to commit
if git diff-index --quiet HEAD --; then
    echo "No changes to commit."
else
    # Commit and push
    git add .
    git commit -m "$commit_message"
    git push origin master
fi
