#!/bin/sh
# wait-for-redis.sh

set -e

host="$1"
shift

echo "Initializing Redis..."
keys=`redis-cli -h $host keys '*'`
while [[ -z "$keys" ]]; do
  sleep 1
  keys=`redis-cli -h $host keys '*'`
done

if [ "$keys" ]; then
    echo "$keys" | while IFS= read -r key; do
        type=`echo | redis-cli -h $host type "$key"`
        case "$type" in
            string) value=`echo | redis-cli -h $host get "$key"`;;
            hash) value=`echo | redis-cli -h $host hgetall "$key"`;;
            set) value=`echo | redis-cli -h $host smembers "$key"`;;
            list) value=`echo | redis-cli -h $host lrange "$key" 0 -1`;;
            zset) value=`echo | redis-cli -h $host zrange "$key" 0 -1 withscores`;;
        esac
        echo "> $key ($type):"
        echo "$value" | sed -E 's/^/    /'
    done
fi

echo "Redis is up - executing command"
exec "$@"