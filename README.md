# JSimple Game Engine (JSGE)
---

# Sobre
Engine simples para desenvolvimento de jogos e simulações básicas. Implementada utilizando como base a engine Raylib (www.raylib.com).

# Como usar
A forma mais fácil de usar é baixar um dos templates disponíveis em https://github.com/davidbuzatto/Templates-JSGE e começar o desenvolvimento. Muitas das funcionalidades implementadas são exemplificadas nos exemplos apresentados no showcase, todos contidos no pacote [br.com.davidbuzatto.jsge.examples](https://github.com/davidbuzatto/JSGE/tree/master/src/br/com/davidbuzatto/jsge/examples). Nas realeases também há um arquivo .zip com a documentação da engine.

Note que a engine depende de algumas bibliotecas nativas para o gerencimamento de controles/gamepads/joysticks, essas parte da [JInput](https://jinput.github.io/jinput/). No template do NetBeans [esses arquivos](https://github.com/davidbuzatto/JSGE/tree/master/lib/jinput-2.0.10-natives-all) serão copiados para o diretório de distribuição automaticamente quando se controi o projeto. No templade do VS Code é necessário fazer tal tarefa manualmente.

Qualquer IDE ou sistema de build para Java tem a capacidade de realizar essa tarefa de cópia, ficando a cargo do usuário configurá-la. Ao executar seu programa, tenha em mente que as bibliotecas nativas tem que estar visíveis para a JVM, seja deixando esses arquivos no mesmo diretório de execução do jogo, seja informando à JVM onde buscar tais arquivos (opção -Djava.library.path) ou colocando-os visíveis sob a variável PATH do sistema operacional.

# Autor
Prof. Dr. David Buzatto

---

# About
Simple engine for developing games and basic simulations. Implemented using the Raylib engine (www.raylib.com) as a base.

# Author
Prof. Dr. David Buzatto
