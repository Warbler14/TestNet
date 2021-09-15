#!/bin/sh
BRANCHES=master

echo "commit message : "
read commitMessage
echo $commitMessage

git add .
git commit -m $commitMessage
git push origin $BRANCHES 
