# Exponenciális Backoff Újrapróbáló Motor

- **Szint:** Medior
- **Fájl:** `Main.java` (Java 17, külső függőség nélkül)

## SaaS kontextus és cél
Átmeneti hálózati/API hibák kezelése exponenciálisan növekvő várakozási idővel (`base * 2^attempt`).

## Fordítás és futtatás
```bash
javac Main.java && java Main
```
