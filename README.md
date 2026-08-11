# Kotlin — Desafios 

## Índice

- [Desafios Kotlin puro](#desafios-kotlin-puro)
- [App1_I18nChallenge — Internacionalização](#app1_i18nchallenge--internacionalização)
- [App2_EletricCar — Layouts, RecyclerView, Fragments e consumo de API](#app2_eletriccar--layouts-recyclerview-fragments-e-consumo-de-api)
- [Fluxo de trabalho com Git](#fluxo-de-trabalho-com-git)
- [Aprendizados gerais](#aprendizados-gerais)

---

## Desafios Kotlin puro

Arquivos `.kt` isolados, sem projeto Android — só lógica em Kotlin.

| Desafio | Descrição |
|---|---|
| Meses por extenso | Converte uma data numérica (`dd/mm/aaaa`) para o mês escrito por extenso. |
| Algarismos romanos | Converte um numeral romano (ex: `MCII`) para um número inteiro. |
| Crescimento populacional | Calcula em quantos anos a população de um país A ultrapassa a de um país B, dadas taxas de crescimento anual diferentes. |
| Modelagem orientada a objetos: `Formacao`, `ConteudoEducacional`, `Usuario`, com matrícula de usuários e validação de duplicidade. |

---

## App1_I18nChallenge — Internacionalização

Projeto Android (Jetpack Compose) demonstrando suporte a múltiplos idiomas (i18n).

**Conceitos aplicados:**
- Arquivos `strings.xml` separados por idioma (`values/`, `values-en/`, `values-es/`), todos com as mesmas *keys*.
- Nenhum texto fixo no código — tudo via `stringResource(id = R.string....)`.
- `@Preview` com parâmetro `locale` para visualizar os idiomas lado a lado sem rodar no emulador.
- Uso do **Translations Editor** do Android Studio para revisar todas as traduções em uma única tabela.

---

## App2_EletricCar — Layouts, RecyclerView, Fragments e consumo de API

Projeto Android (Views + XML) que evoluiu em etapas, replicando o mockup de um app de listagem de carros elétricos.

### Etapas do desenvolvimento

1. **Layout estático** — Tela replicada usando `ConstraintLayout`, `TextView`, abas "Carros"/"Favoritos".
2. **Navegação entre Activities** — `MainActivity` → `CalcularAutonomiaActivity`, usando `Intent` e `startActivity`. Toda Activity nova precisa ser **declarada no `AndroidManifest.xml`**, senão a navegação falha silenciosamente (`ActivityNotFoundException`).
3. **RecyclerView + Adapter** — Lista de carros renderizada de forma performática, reciclando Views em vez de criar uma nova para cada item.
   - `Adapter`: ponte entre os dados e a tela.
   - `ViewHolder`: guarda as referências das Views de um cartão (evita `findViewById` repetido).
   - `onCreateViewHolder`: cria a estrutura vazia de um cartão (chamado poucas vezes).
   - `onBindViewHolder`: preenche um cartão com os dados de uma posição específica (chamado toda vez que um item aparece na tela).
   - `getItemCount`: informa quantos itens existem no total.
4. **Fragments + ViewPager2 + TabLayout** — Migração da lista de carros para dentro de um `Fragment` (`CarFragment`), permitindo trocar de aba ("Carros"/"Favoritos") sem recriar a Activity inteira.
5. **Consumo de API remota** — Busca de dados reais de carros via `HttpURLConnection` + `AsyncTask`, com parsing manual de JSON (`JSONTokener`, `JSONArray`, `JSONObject`).

### Estrutura de pacotes

```
com.example.app2_eletriccar
├── data/          → CarFactory (dados estáticos de teste)
├── dominio/        → Carro (data class / model)
├── presentation/   → MainActivity, CalcularAutonomiaActivity, CarFragment
└── ui.adapter/     → CarAdapter, TabAdapter
```

### Bugs encontrados e corrigidos (para revisão)

| Sintoma | Causa | Correção |
|---|---|---|
| App fecha ao clicar em botão de navegação | Activity nova não declarada no `AndroidManifest.xml` | Adicionar `<activity android:name=".presentation.NomeDaActivity" />` |
| `Unresolved reference: main` no Kotlin | Layout raiz sem `android:id="@+id/main"` | Adicionar o id no elemento raiz do XML |
| Só aparece 1 card no RecyclerView | `layout_height="match_parent"` no item da lista | Trocar para `layout_height="wrap_content"` |
| `Unresolved reference: add` numa lista | Variável tipada como `List` (imutável) em vez de `MutableList` | Declarar como `MutableList<T>` / usar `mutableListOf()` |
| App fecha sem erro (`lateinit property has not been initialized`) | Variável `lateinit` nunca recebeu `findViewById` | Inicializar todas as `lateinit var` em `setupViews()` |
| `ViewPager2` não mostra conteúdo | Constraints incompletas/contraditórias (`Bottom_toTopOf="parent"`, faltando `End`) | Usar `Bottom_toBottomOf="parent"` e adicionar `Start`/`End` em relação ao pai |
| Manifest não compila: `<uses-permission>` inválido | Permissão declarada dentro de `<application>` | Mover `<uses-permission>` para fora de `<application>`, direto em `<manifest>` |
| App conecta na API mas carros não aparecem | Chave do JSON (`urlPhoto`) diferente da usada no parsing (`url_Photo`) | Ajustar `getString(...)` para bater exatamente com a chave do JSON |

---

## Fluxo de trabalho com Git

- Um repositório único (`Kotlin/`), com uma pasta por desafio.
- `.gitignore` na **raiz** do repositório cobrindo `.idea/`, `build/`, `.gradle/`, `local.properties` — vale para todas as pastas de projeto automaticamente.
- Antes de cada commit: `git status` para conferir o que está staged, garantindo que nenhuma pasta de configuração local (`.idea`) suba por engano.
- Commits separados por desafio/funcionalidade (ex: "Adiciona RecyclerView com Adapter para lista de carros").

---

## Aprendizados gerais

- **Ciclo de vida da Activity**: `onCreate` → `onStart` → `onResume` (ativa) → `onPause` → `onStop` (inativa) → `onDestroy`. `onPause`/`onResume` se repetem toda vez que o app perde/recupera o foco.
- **Fragments**: pedaços reutilizáveis de UI, hospedados dentro de uma Activity — úteis para abas, telas adaptáveis (celular vs tablet), e trocar conteúdo sem recriar a Activity.
- **Debugging com Logcat**: filtrar por tag (`tag:MinhaTag`) ou nível de severidade (Error) é essencial quando o app "não faz nada" — geralmente é um crash silencioso, não ausência de erro.
  - `Log.d` → mensagens de debug (fluxo normal).
  - `Log.e` → erros reais, usados dentro de blocos `catch`.
- **AsyncTask está deprecated** — próximo passo natural de evolução é migrar chamadas assíncronas para **Coroutines**.

---

*Última atualização: desafio App2_EletricCar (RecyclerView + Fragments + consumo de API).*
