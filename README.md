# Java SaaS Lab — 24 Junior → Medior Java 17 Alkalmazás ☕

**24 önálló, lefordított és tesztelt Java 17 (külső függőség nélküli) SaaS építőkockát** tartalmazó portfólió repó — `record`, `BigDecimal`, `MessageDigest`, `HmacSHA256`, `ThreadLocal` multi-tenancy, LRU/TTL cache, Circuit Breaker, Hash-láncos audit napló és állapotgép.

🌐 Portfólió: [cuppashot.hu](https://cuppashot.hu) · 👤 Készítette: **Pék Richárd**

## Összes projekt fordítása és futtatása
```bash
bash run_all_tests.sh
```

## Projektek (24 db)

| # | Alkalmazás | Szint | Leírás |
|---|---|---|---|
| `01` | **[API-kulcs generátor és SHA-256 ellenőrző](./01-api-key-manager/)** | Junior | Prefixelt SaaS API-kulcsok kibocsátása, SHA-256 hash tárolás, scope ellenőrzés és visszavonás Java 17-ben. |
| `02` | **[Token Bucket API Rate Limiter](./02-token-bucket-limiter/)** | Junior+ | Ügyfelenkénti (per-tenant) API kéréskorlátozó Java 17-ben determinisztikus órával. |
| `03` | **[Webhook HMAC-SHA256 aláíró és ellenőrző](./03-webhook-hmac-verifier/)** | Junior+ | Webhook payloadok kriptográfiai aláírása (`javax.crypto.Mac`) és időablakos replay-védelem. |
| `04` | **[Előfizetés-időarányosító (BigDecimal Proration)](./04-subscription-proration/)** | Junior+ | Pontos `BigDecimal` alapú SaaS csomagváltási különbözet és 27% ÁFA számítás. |
| `05` | **[Multi-Tenant RBAC Jogosultsági Motor](./05-rbac-engine/)** | Junior+ | Szerepkör-alapú jogosultságellenőrzés wildcard (`projects:*`) és tenant-izolációval. |
| `06` | **[Feature Flag & Százalékos Rollout Kiértékelő](./06-feature-flag-rollout/)** | Medior | Determinisztikus hash-alapú fokozatos funkciókapcsoló VIP whitelist támogatással. |
| `07` | **[TTL + LRU Memóriabeli Gyorsítótár](./07-ttl-lru-cache/)** | Medior | `LinkedHashMap` access-order alapú LRU kilakoltatás és lejárati idő (TTL) kezelés. |
| `08` | **[Háttérfeladat-sor Retry & Dead-Letter Queue-val](./08-job-queue-dlq/)** | Medior | Aszinkron munkafeldolgozó sor automatikus újrapróbálással és hibás feladatok DLQ-ba mentésével. |
| `09` | **[SHA-256 Hash-láncos Audit Napló](./09-audit-hash-chain/)** | Medior | Vállalati audit napló, amely kriptográfiai láncolással azonnal kiszűri az utólagos manipulációt. |
| `10` | **[Circuit Breaker Külső Szolgáltatásokhoz](./10-circuit-breaker/)** | Medior | CLOSED / OPEN / HALF_OPEN állapotgép külső API kiesések izolálására. |
| `11` | **[Használatalapú Fogyasztásmérő (Usage Metering)](./11-usage-metering/)** | Junior+ | Ügyfelenkénti API-egység aggregátor ingyenes kerettel és túllépési díjszámítással. |
| `12` | **[Idempotency-Key Tároló Fizetési API-hoz](./12-idempotency-store/)** | Medior | Ismételt POST fizetési kérések kiszűrése és korábbi válasz visszaadása dupla terhelés nélkül. |
| `13` | **[Jóváhagyási Állapotgép (Workflow State Machine)](./13-workflow-state-machine/)** | Junior+ | Enum-alapú, szigorúan ellenőrzött állapotátmenetek dokumentum-jóváhagyáshoz. |
| `14` | **[SLA & Error Budget Kalkulátor](./14-sla-error-budget/)** | Junior | Havi rendelkezésre állási SLA százalék és fennmaradó hiba-költségkeret számító. |
| `15` | **[Multi-Tenant Context & Adat-izolációs Réteg](./15-tenant-context-isolation/)** | Medior | `ThreadLocal` alapú ügyfél-kontextus és biztonságos adattár-szűrés. |
| `16` | **[Eseménybusz (In-Memory Pub/Sub)](./16-event-bus-pubsub/)** | Junior+ | Típusos domain-események (pl. `InvoicePaid`, `UserInvited`) szétosztása feliratkozók között. |
| `17` | **[Deklaratív Adatvalidációs Csővezeték](./17-validation-pipeline/)** | Junior+ | Komponálható mező-validátor SaaS regisztrációs és számlázási űrlapokhoz. |
| `18` | **[Mini REST API Router & Middleware](./18-mini-rest-router/)** | Medior | Útválasztó és válasz-módosító middleware minta Java 17 recordokkal. |
| `19` | **[Strukturált Számla-összesítő Generátor](./19-invoice-text-generator/)** | Junior | Tételsorokból formázott nettó/ÁFA/bruttó számla-összesítő `BigDecimal` pontossággal. |
| `20` | **[AI Token & Költségbecslő Modul (Java)](./20-ai-prompt-cost-estimator/)** | Medior | Prompt-sablon renderelés és LLM API token-költség becslés Java SaaS backendekhez. |
| `21` | **[HMAC-SHA256 Aláírt Token Motor (Java 17)](./21-signed-token/)** | Medior | Base64url + `HmacSHA256` stateless hitelesítési token generálás és lejárati idő ellenőrzés. |
| `22` | **[Hierarchikus Multi-Tenant Konfiguráció-feloldó](./22-multi-tenant-config/)** | Medior | Globális alapérték → előfizetési csomag → ügyfélszintű felülírás kezelése. |
| `23` | **[HTML-Escape-elt Tranzakciós Sablonmotor](./23-template-engine/)** | Junior+ | Változó-behelyettesítő és XSS-elleni HTML-escape motor kötelező kulcs ellenőrzéssel. |
| `24` | **[Exponenciális Backoff Újrapróbáló Motor](./24-retry-backoff/)** | Medior | Átmeneti hálózati/API hibák kezelése exponenciálisan növekvő várakozási idővel (`base * 2^attempt`). |
