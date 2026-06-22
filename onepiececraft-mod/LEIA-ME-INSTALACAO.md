# One Piece Craft — Mod para Minecraft Java 1.21.1 + Forge

## Como instalar

### 1. Instale o Forge 1.21.1
- Baixe o Forge em: https://files.minecraftforge.net/net/minecraftforge/forge/
- Versão necessária: **Forge 52.0.33 para MC 1.21.1**
- Execute o instalador e escolha "Install Client"

### 2. Compile o mod (necessário Java 21 e Gradle)

```bash
# Na pasta do mod:
./gradlew build
```

O arquivo JAR será gerado em: `build/libs/onepiececraft-1.0.0.jar`

### 3. Instale o mod
- Copie o arquivo `.jar` para a pasta `mods` do Minecraft
  - Windows: `%APPDATA%\.minecraft\mods\`
  - Linux/Mac: `~/.minecraft/mods/`

### 4. Inicie o Minecraft com o perfil Forge 1.21.1

---

## Controles

| Tecla | Ação |
|-------|------|
| **G** (1ª vez) | Escolher facção (Pirata ou Marinheiro) |
| **G** (2ª vez) | Abrir painel de status completo |
| **1** | Ataque da Fruta 1 |
| **2** | Ataque da Fruta 2 |
| **3** | Ataque da Fruta 3 |
| **4** | Ataque da Fruta 4 |
| **5** | Ataque da Fruta 5 |
| **6** | Ataque da Fruta 6 |
| **R** | Habilidade da Raça 1 (desbloqueia no Nível 5) |
| **F** | Habilidade da Raça 2 (desbloqueia no Nível 35) |

---

## Conteúdo do Mod

### Frutas do Diabo (10)
| Fruta | Tipo | Ataques |
|-------|------|---------|
| Gomu Gomu no Mi | Paramecia | 4 |
| Mera Mera no Mi | Logia | 5 |
| Hie Hie no Mi | Logia | 5 |
| Gura Gura no Mi | Paramecia | 6 |
| Ope Ope no Mi | Paramecia | 5 |
| Yami Yami no Mi | Logia | 6 |
| Pika Pika no Mi | Logia | 5 |
| Suna Suna no Mi | Logia | 4 |
| Goro Goro no Mi | Logia | 5 |
| Tori Tori no Mi (Fênix) | Zoan | 4 |

**Como encontrar frutas:**
- **5% de chance** em qualquer baú do jogo
- **75% de chance** no baú único da Ilha do Tesouro (gerada no oceano)

**Regras:**
- Comer 2 frutas = morte instantânea + perde ambas
- Ao morrer normalmente, você NÃO perde sua fruta equipada
- Na água: -vida e -stamina passivamente

### Armas
| Arma | Crafting |
|------|---------|
| Katana | 2 Ferro + Cabo |
| Wado Ichimonji | 2 Diamante + Cabo |
| Sandai Kitetsu | Diamante + Ouro de Tolo + Cabo |
| Shusui | 2 Netherite + Cabo |
| Nidai Kitetsu | Mesa de crafting avançada |
| Bisento | 3 Netherite + 2 Cabos |
| Yoru | Ouro de Tolo + Netherite + Diamante + Vara de Blaze |

### Minério: Ouro de Tolo (Pirita)
- Encontrado nas camadas -16 a +32
- Versão de Deepslate nas camadas mais profundas
- Minerado com picareta de ferro ou melhor
- 9 pepitas = 1 lingote

### Raças (aleatória ao entrar)
| Raça | Passivo | Habilidade R (Lv.5) | Habilidade F (Lv.35) |
|------|---------|---------------------|----------------------|
| Humano | XP +15% | Resolução (resistência) | Potencial Infinito |
| Fishman | Nado 2x, sem dano na água | Golpe Aquático | Karate Fishman |
| Mink | Velocidade +10% | Electro | Sulong |
| Lunária | Imune ao fogo | Asas de Fogo | Imortalidade Breve |
| Gigante | Vida +40, Dano +10% | Golpe Colossal | Forma Gigante |
| Zoan Antigo | Visão noturna | Forma Parcial | Forma Completa |

### Facções
- **Pirata**: Velocidade +5%, Dano +5%, Sistema de Recompensa
- **Marinheiro**: Resistência +5%, Sistema de Honra (ao matar criaturas do mal)

### NPCs
- **Soldado da Marinha**: Aliado de Marinheiros, ataca Piratas
- **Membro Pirata**: Aliado de Piratas, ataca Marinheiros

---

## Como compilar (para desenvolvedores)

**Pré-requisitos:**
- Java 21 (JDK)
- Gradle 8.8 (ou use o wrapper incluído `./gradlew`)

```bash
# Baixe as dependências do Forge
./gradlew dependencies

# Compile
./gradlew build

# Rode em ambiente de desenvolvimento
./gradlew runClient
```

---

## Créditos
- Mod criado com base na obra One Piece de Eiichiro Oda
- Todas as referências a One Piece pertencem a seus respectivos donos
