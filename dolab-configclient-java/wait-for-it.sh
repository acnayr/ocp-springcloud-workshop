#!/bin/sh
# wait-for-config-server.sh

set -e

host="$1"
shift

echo "Initializing Config Server..."
until [ $(curl -LI http://$host:8888/actuator/health -o /dev/null -w '%{http_code}\n' -s) == "200" ]; do
    echo "Executing health check..."
    sleep 1
done

echo "Config Server is up - executing command"
exec "$@"