#!/bin/bash
echo "============================================"
echo "  ONE PIECE CRAFT - Compilador do Mod"
echo "  Minecraft 1.21.1 + Forge"
echo "============================================"
echo ""

# Verifica Java
if ! command -v java &> /dev/null; then
    echo "[ERRO] Java não encontrado!"
    echo ""
    echo "Instale o Java 21:"
    echo "  Mac: brew install openjdk@21"
    echo "  Linux Ubuntu/Debian: sudo apt install openjdk-21-jdk"
    exit 1
fi

echo "[OK] Java encontrado!"
echo ""
echo "Compilando o mod (pode demorar na primeira vez)..."
echo ""

chmod +x gradlew
./gradlew build --no-daemon

if [ $? -ne 0 ]; then
    echo ""
    echo "[ERRO] Falha na compilação!"
    exit 1
fi

echo ""
echo "============================================"
echo "  COMPILADO COM SUCESSO!"
echo "============================================"
echo ""
echo "Arquivo gerado: build/libs/onepiececraft-1.0.0.jar"
echo ""
echo "Copie para a pasta mods do Minecraft:"
echo "  Mac: ~/Library/Application Support/minecraft/mods/"
echo "  Linux: ~/.minecraft/mods/"
