#!/bin/bash
SONAR_HOST="http://localhost:9000"
SONAR_TOKEN="squ_7c95544cd5684a71b8b7d9d927b8ea17a0a7b497"

echo "⏳ Aguardando SonarQube..."
until curl -s "$SONAR_HOST/api/system/status" | grep '"status":"UP"'; do
  sleep 5
done

echo "🚀 Enviando para o Sonar..."
mvn clean verify sonar:sonar \
  -Dsonar.host.url="$SONAR_HOST" \
  -Dsonar.token="$SONAR_TOKEN" \
  -DskipTests=false