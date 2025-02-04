# JSimple Game Engine (JSGE)

<img align="left" style="width:260px" src="https://github.com/davidbuzatto/JSGE/blob/master/resources/images/logoJSGE.png" width="288px">

A JSGE é uma engine simples para desenvolvimento de jogos e simulações em Java. Ela atua como uma camada de abstração ao [Java2D](https://docs.oracle.com/javase/tutorial/2d/index.html), tornando as operações de desenho mais transparentes e gerenciando diversas funcionalidades inerentes ao desenvolvimento de jogos. A motivação para seu desenvolvimento inicial foi prover aos meus alunos, estudantes das disciplinas introdutórias de programação orientada a objetos, uma forma mais simples de desenhar figuras geométricas em Java. Essa abstração inicial cresceu ao ponto de eu decidir implementar uma engine que tem funcionamento e filosofia iguais à excelente engine de jogos [Raylib](https://www.raylib.com). A documentação provida no código fonte e disponibilizada via Javadoc está em português, pois meu foco são meus alunos, mas nada impede que futuramente seja traduzida para inglês.

## Como usar
A forma mais fácil de usar é baixar um dos templates disponíveis em https://github.com/davidbuzatto/Templates-JSGE e começar o desenvolvimento. Muitas das funcionalidades implementadas são exemplificadas nos exemplos apresentados no showcase, todos contidos no pacote [br.com.davidbuzatto.jsge.examples](https://github.com/davidbuzatto/JSGE/tree/master/src/br/com/davidbuzatto/jsge/examples). Aliás, todo usuário é encorajado em explorar o código fonte da engine para entender como as coisas funcionam e quais as funcionalidades disponíveis. Nas realeases também há um arquivo .zip com a documentação da engine.

Note que a engine depende de algumas bibliotecas nativas para o gerencimamento de controles/gamepads/joysticks, essas parte da [JInput](https://jinput.github.io/jinput/). No template do NetBeans [esses arquivos](https://github.com/davidbuzatto/JSGE/tree/master/lib/jinput-2.0.10-natives-all) serão copiados para o diretório de distribuição automaticamente quando se controi o projeto. Nos outros templates é necessário configurar as ferramentas apropriadamente para que isso seja feito.

Qualquer IDE ou sistema de build para Java tem a capacidade de realizar essa tarefa de cópia, ficando a cargo do usuário configurá-la. Ao executar seu programa, tenha em mente que as bibliotecas nativas tem que estar visíveis para a JVM, seja deixando esses arquivos no mesmo diretório de execução do jogo, seja informando à JVM onde buscar tais arquivos (opção -Djava.library.path) ou colocando-os visíveis sob a variável PATH do sistema operacional.

Ainda, o showcase com os exemplos de utilização da engine está disponível em outro repositório: [JSGE-Showcase](https://github.com/davidbuzatto/JSGE-Showcase).

## Dependências
- [Java Stream Player](https://github.com/goxr3plus/java-stream-player): processamento de áudio;
- [JInput](https://github.com/jinput/jinput): gerenciamento de controles/gamepads/joysticks.

---

<img align="left" style="width:260px" src="https://github.com/davidbuzatto/JSGE/blob/master/resources/images/logoJSGE.png" width="288px">

JSGE is a simple engine for developing games and simulations in Java. It acts as an abstraction layer to [Java2D](https://docs.oracle.com/javase/tutorial/2d/index.html), making drawing operations more transparent and managing several features inherent to game development. The motivation for its initial development was to provide my students of introductory object-oriented programming courses, with a simpler way to draw geometric figures in Java. This initial abstraction grew to the point that I decided to implement an engine that has the same operation and philosophy as the excellent game engine [Raylib](https://www.raylib.com). The documentation provided in the source code and made available via Javadoc is in Portuguese, since my focus is on my students, but there is nothing stopping it from being translated into English in the future.

## How to use
The easiest way to use it is to download one of the templates available at https://github.com/davidbuzatto/Templates-JSGE and start developing. Many of the features are exemplified in the examples presented in the showcase, all contained in the [br.com.davidbuzatto.jsge.examples](https://github.com/davidbuzatto/JSGE/tree/master/src/br/com/davidbuzatto/jsge/examples) package. In fact, every user is encouraged to explore the engine's source code to understand how things work and what features are available. In the releases there is also a .zip file with the engine's documentation (writen in brazilian portuguese).

Note that the engine depends on some native libraries for managing controls/gamepads/joysticks, these are part of [JInput](https://jinput.github.io/jinput/). No NetBeans template [these files](https://github.com/davidbuzatto/JSGE/tree/master/lib/jinput-2.0.10-natives-all) will be copied to the distribution directory automatically when the project is checked. In other templates, you need to configure the tools appropriately for this to be done.

Any Java IDE or build system has the ability to perform this copying task, leaving the user load configured. When running your program, keep in mind that the native libraries must be visible to the JVM, either by leaving these files in the same directory as the game execution, or by telling the JVM where to look for such files (-Djava.library.path option) or by making them visible under the operating system PATH variable.

Still, the showcase with examples of engine usage is available in another repository: [JSGE-Showcase](https://github.com/davidbuzatto/JSGE-Showcase).

## Dependencies
- [Java Stream Player](https://github.com/goxr3plus/java-stream-player): audio processing;
- [JInput](https://github.com/jinput/jinput): controller/gamepad/joystick management.
