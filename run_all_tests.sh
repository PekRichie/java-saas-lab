#!/usr/bin/env bash
set -e
PASS=0
for d in [0-9][0-9]-*/; do
  (cd "$d" && javac Main.java && java Main && rm -f *.class)
  PASS=$((PASS+1))
done
echo "Összesen $PASS / 20 Java 17 alkalmazás sikeresen lefordult és lefutott!"
