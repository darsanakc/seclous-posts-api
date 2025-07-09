#!/bin/sh

set -euo pipefail

API=http://server:8080/posts

echo "⏳ Waiting for API server to become available..."

# Wait until server is ready (up to 15 seconds)
for i in $(seq 1 5); do
  if curl -s --head "$API" | grep "200 OK" > /dev/null; then
    break
  fi
  echo "  🕒 Attempt $i: server not ready yet..."
  sleep 3
done

echo "✅ Server is up! Starting tests..."

echo "1️⃣ Creating post"
POST_ID=$(curl -s -X POST -H "Content-Type: application/json" -d \
  '{"author":"Alice","date":"'$(date -u +"%Y-%m-%dT%H:%M:%SZ")'","content":"Hello"}' $API | jq -r .id)

echo "✔️ Post created with ID: $POST_ID"

echo "2️⃣ Listing posts"
curl -fs $API | jq

echo "3️⃣ Updating post"
curl -fs -X PUT -H "Content-Type: application/json" -d \
  '{"author":"Alice","date":"'$(date -u +"%Y-%m-%dT%H:%M:%SZ")'","content":"Updated post"}' $API/$POST_ID | jq

echo "4️⃣ Deleting post"
curl -fs -X DELETE $API/$POST_ID

echo "5️⃣ Verifying deletion"
STATUS=$(curl -s -o /dev/null -w "%{http_code}" $API/$POST_ID)

if [ "$STATUS" -eq 404 ]; then
  echo "✅ All tests passed successfully!"
else
  echo "❌ Delete verification failed!"
  exit 1
fi
