# JSimple Game Engine (JSGE)

<img align="left" style="width:260px" src="https://github.com/davidbuzatto/JSGE/blob/master/resources/images/logoJSGE.png" width="288px">

A JSGE é uma engine simples para desenvolvimento de jogos e simulações em Java. Ela atua como uma camada de abstração ao [Java2D](https://docs.oracle.com/javase/tutorial/2d/index.html), tornando as operações de desenho mais transparentes e gerenciando diversas funcionalidades inerentes ao desenvolvimento de jogos. A motivação para seu desenvolvimento inicial foi prover aos meus alunos, estudantes das disciplinas introdutórias de programação orientada a objetos, uma forma mais simples de desenhar figuras geométricas em Java. Essa abstração inicial cresceu ao ponto de eu decidir implementar uma engine com funcionamento e filosofia semelhantes à excelente engine de jogos [Raylib](https://www.raylib.com). A documentação disponível no código-fonte e via Javadoc está em inglês.

## Como usar

A forma mais fácil de começar é baixar um dos templates disponíveis no repositório [Templates-JSGE](https://github.com/davidbuzatto/Templates-JSGE) e iniciar o desenvolvimento a partir dele. Muitas das funcionalidades implementadas são exemplificadas no repositório [JSGE-Showcase](https://github.com/davidbuzatto/JSGE-Showcase), que reúne diversos exemplos de uso da engine. Todo usuário é encorajado a explorar o código-fonte da engine para entender como as coisas funcionam e quais funcionalidades estão disponíveis. Nas releases também está disponível um arquivo `.zip` com a documentação gerada via Javadoc.

Note que a engine depende de algumas bibliotecas nativas para o gerenciamento de controles/gamepads/joysticks, que fazem parte do projeto [JInput](https://jinput.github.io/jinput/). No template para o NetBeans, [esses arquivos](https://github.com/davidbuzatto/JSGE/tree/master/lib/jinput-2.0.10-natives-all) são copiados automaticamente para o diretório de distribuição ao construir o projeto. Nos demais templates, é necessário configurar a ferramenta de build adequadamente para que isso ocorra.

Qualquer IDE ou sistema de build para Java é capaz de realizar essa tarefa de cópia, cabendo ao usuário fazer a configuração necessária. Ao executar o programa, tenha em mente que as bibliotecas nativas precisam estar visíveis para a JVM — seja colocando os arquivos no mesmo diretório de execução do jogo, seja informando à JVM onde encontrá-los por meio da opção `-Djava.library.path`, seja incluindo-os na variável de ambiente `PATH` do sistema operacional.

## Dependências
- [Java Stream Player](https://github.com/goxr3plus/java-stream-player): processamento de áudio;
- [JInput](https://github.com/jinput/jinput): gerenciamento de controles/gamepads/joysticks.

---

<img align="left" style="width:260px" src="https://github.com/davidbuzatto/JSGE/blob/master/resources/images/logoJSGE.png" width="288px">

JSGE is a simple engine for developing games and simulations in Java. It acts as an abstraction layer on top of [Java2D](https://docs.oracle.com/javase/tutorial/2d/index.html), making drawing operations more straightforward and managing several features inherent to game development. The motivation for its initial development was to provide my students in introductory object-oriented programming courses with a simpler way to draw geometric figures in Java. This initial abstraction grew to the point where I decided to implement an engine with the same operation and philosophy as the excellent game engine [Raylib](https://www.raylib.com). The source code documentation is available in English, both inline and via Javadoc.

## How to use

The easiest way to get started is to download one of the templates available in the [Templates-JSGE](https://github.com/davidbuzatto/Templates-JSGE) repository and begin development from there. Many of the engine's features are demonstrated in the [JSGE-Showcase](https://github.com/davidbuzatto/JSGE-Showcase) repository, which contains a variety of usage examples. Every user is encouraged to explore the engine's source code to understand how things work and what features are available. The releases also include a `.zip` file with the Javadoc-generated documentation.

Note that the engine depends on some native libraries for managing controls/gamepads/joysticks, which are part of the [JInput](https://jinput.github.io/jinput/) project. In the NetBeans template, [these files](https://github.com/davidbuzatto/JSGE/tree/master/lib/jinput-2.0.10-natives-all) are automatically copied to the distribution directory when the project is built. In other templates, you will need to configure your build tool appropriately to ensure this happens.

Any Java IDE or build system is capable of performing this copy task — it is up to the user to set it up correctly. When running your program, keep in mind that the native libraries must be visible to the JVM: either by placing the files in the same directory as the game executable, by specifying the path via the `-Djava.library.path` JVM option, or by adding them to the operating system's `PATH` environment variable.

## Dependencies
- [Java Stream Player](https://github.com/goxr3plus/java-stream-player): audio processing;
- [JInput](https://github.com/jinput/jinput): controller/gamepad/joystick management.
