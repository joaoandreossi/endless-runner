import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

/*CLASSE: Responsável por tratar da movimentação do personagem e associar cada animação ao estado
          correspondente. Herda da classe JPanel para poder dar override na função 'paintComponent'
          que será chamada toda vez que a tela for atualizada e irá desenhar o sprite atual da animação.*/
public class Player extends JPanel {

    /*VARIÁVEIS: int posX:   coordenada x da tela onde JPanel que contém o sprite será desenhado
      DE         int posY:   coordenada y da tela onde JPanel que contém o sprite será desenhado
      POSIÇÃO    int width:  largura do JPanel que o sprite estará contido
                 int height: altura do JPanel que o sprite estará contido*/
    private int posX;
    private int posY;
    private int width;
    private int height;

    /*VARIÁVEIS: Sprite a:                  instância da classe Sprite que contém o frame sheet da 
      DE                                    imagem 'walking.png'
      ANIMAÇÃO   Sprite b:                  instância da classe Sprite que contém o frame sheet da 
                                            imagem 'idle.png'
                 Sprite c:                  instância da classe Sprite que contém o frame sheet da 
                                            imagem 'jumping.jpg'
                 BufferedImage walkSprites: vetor de sprites gerados a partir do frame sheet 'a'
                 BufferedImage idleSprites: vetor de sprites gerados a partir do frame sheet 'b'
                 BufferedImage jumpSprites: vetor de sprites gerados a partir do frame sheet 'c'
                 Animation walking:         cria uma nova instância da classe Animation, que recebe como
                                            parâmetro do construtor um vetor de sprites e a duração deles
                 Animation idle:            cria uma nova instância da classe Animation, que recebe como
                                            parâmetro do construtor um vetor de sprites e a duração deles
                 Animation jumping:         cria uma nova instância da classe Animation, que recebe como
                                            parâmetro do construtor um vetor de sprites e a duração deles
                 Animation animation:       instância vazia da classe Animation que será utilizada por
                                            todas as funções desta classe, sempre receberá como valor
                                            alguma das outras animações instanciadas, dependendo do
                                            estado do personagem*/
    private Sprite a = new Sprite("walking");
    private Sprite b = new Sprite("idle");
    private Sprite c = new Sprite("jumping");
    private BufferedImage[] walkSprites = a.getSprites(10, 14, 32);
    private BufferedImage[] idleSprites = b.getSprites(16, 14, 32);
    private BufferedImage[] jumpSprites = c.getSprites(17, 20, 31);
    private Animation walking = new Animation(walkSprites, 5);
    private Animation idle = new Animation(idleSprites, 10);
    private Animation jumping = new Animation(jumpSprites, 2);
    private Animation animation = idle;

    /*VARIÁVEIS: int direction:             indica se o sprite deverá ser desenhado virado para a 
      DE                                    direita (1) ou para a esquerda (-1)
      MOVIMENTO  boolean grounded:          indica se o personagem se encontra no chão (true) ou no 
                                            ar (false)
                 boolean isWalking:         indica se o personagem está se movimentando (true) ou se está 
                                            parado (false)
                 double horizontalVelocity: indica a quantidade que a posição x do sprite é incrementada 
                                            a cada frame, é inicializada em 0 pois o personagem começa 
                                            parado (quanto maior esse valor, mais rápido ele se move
                                            horizontalmente)
                 double verticalVelocity:   indica a posição y inicial do personagem quando ele inicia 
                                            o pulo, esse valor é decrementado pela gravidade a cada 
                                            frame (quanto maior esse valor, mais alto é o pulo)
                 double gravity:            a quantidade que a posição y do personagem é decrementada 
                                            após um pulo (quanto maior esse valor, mais rápido o 
                                            personagem cai após um pulo)*/
    private int direction = 1;
    private boolean isGrounded = true;
    private boolean isWalking = false;

    private double horizontalVelocity = 0;
    private double verticalVelocity = 25;
    private double gravity = -2;

    /*CONSTRUTOR: Recebe como parâmetro uma coordenada (x,y) que representa aonde o sprite deverá ser
                  desenhado na tela e a largura 'w' e altura 'h' do sprite. Também inicia a animação 
                  inicial atráves do método 'start' da classe Animation (no caso o personagem começa 
                  parado, então animação 'idle').*/
    protected Player(int x, int y, int w, int h) {
        posX = x;
        posY = y;
        width = w;
        height = h;
        animation.start();
    }

    /*MÉTODO: Método da classe JPanel que é chamada cada vez que a tela é atualizada. O método 
              'drawImage' recebe como parâmetro a imagem a ser desenhada, as coordenadas (x,y) da 
              posição da tela onde será posicionada, e a escala (w,h) que indica a largura e a altura
              do sprite.
              
              Verificamos a variável 'direction' para indicar se o sprite deverá ser desenhado
              normalmente (direction = 1) ou invertida (direction = -1).*/
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(direction == 1){
            g.drawImage(animation.getSprite(), posX+width*2, posY-height*2, width*2, height*2, null);
        } else {
            g.drawImage(animation.getSprite(), posX+width*4, posY-height*2, -width*2, height*2, null);
        }
    }

    /*MÉTODO: Responsável por movimentar o sprite horizontalmente na tela. Recebe apenas um parâmetro 
              'dir' do tipo int que indica se o movimento é para a direita ou para a esquerda. O flag
              de movimento 'isWalking' é setado para indicar que o personagem está em movimento.
              
              O flag 'grounded' é checado toda vez para verificar se o personagem se encontra no meio
              de um pulo, caso sim, ele se mantém na animação do pulo, se não, a animação de andar é
              setada e iniciada. Fora isso, o método apenas muda a velocidade horizontal para algum valor
              diferente de 0.*/
    protected void move(int dir){
        direction = dir;
        isWalking = true;
        if(isGrounded){
            animation = walking;
            animation.start();
        }
        if(dir == 1){
            horizontalVelocity = 3;
            
        } else {
            horizontalVelocity = -3;
        }
    }

    /*MÉTODO: Apenas seta o flag 'isWalking' para falso de maneira a indicar que o personagem está 
              parado, o que é importante para a função de update.*/
    protected void stopWalking(){
        isWalking = false;
    }

    /*MÉTODO: Responsável por setar a animação de pulo caso o personagem esteja no chão, e em seguida 
              muda o flag 'grounded' para false, assim o personagem não pode pular novamente caso já
              esteja no ar.*/
    protected void jump(){
        if(isGrounded){
            animation = jumping;
            animation.start();
            isGrounded = false;
        }
    }

    /*MÉTODO: Verifica se o personagem está no chão e parado, caso sim, inicia a animação 'idle' e seta
              a velocidade horizontal para 0.*/
    protected void stop(){
        if(isGrounded && !isWalking){
            animation = idle;
            animation.start();
            horizontalVelocity = 0;
        }
    }

    /*MÉTODO: Responsável por receber o código da tecla apertada e chamar as funções responsáveis pelo
              movimento associado àquela tecla.*/
    protected void changeAnimation(KeyEvent e){
        switch(e.getKeyCode()){
            case KeyEvent.VK_D:
                move(1);
                break;
            case KeyEvent.VK_A:
                move(-1);
                break;
            case KeyEvent.VK_W:
                jump();
                break;
            default:
                break;
        }
    }

    /*MÉTODO: Responsável pelo cálculo do movimento do personagem. Esse método deve ser colocado no loop
              principal do programa, assim será chamado uma vez para todo frame.
              
              Primeiro o método de update da animação é chamado. Como esse método é executado todo frame,
              a animação também se atualiza todo frame, dando a fluidez necessária.
              
              Uma verificação se o personagem está no ar é feita, caso sim, a posição (x,y) é acrescida
              da velocidade vertical e horizontal (isso permite o personagem fazer um arco de parábola
              se estiver se movimentando horizontalmente ao mesmo tempo do pulo). A velocidade vertical
              é então decrescida da gravidade a cada frame, isso faz o personagem descer após um pulo, já
              que a partir de certo momento a velocidade vertical terá seu sinal alterado e a direção vai
              se inverter.
              
              Para garantir que o personagem não ultrapasse o 'chão', é verificado se a posição y dele
              é maior que a posição y do chão, caso sim, ele é colocado na mesma altura do chão e sua
              flag 'isGrounded' é setado para true, indicando que o personagem não está mais no ar. Após
              isso, sua velocidade vertical é resetada (pois o valor inicial foi alterado pela gravidade).
              
              Para evitar que a animação 'idle' comece a tocar caso o usuário ainda esteja segurando
              alguma tecla de movimento horizontal, o flag 'isWalking' é verificado, e caso esteja ativo
              a animação de andar toma prioridade e é executada no lugar da 'idle', mas caso não, a
              função 'stop' é chamada e faz sua própria verificação dos flags para decidir se a animação
              deve ou não ser começada.
              
              Por fim, a velocidade horizontal é acrescida na posição x, caso o usuário não esteja
              apertando nenhuma tecla de movimento horizontal, esse valor é 0 e a posição não é 
              alterada.*/
    protected void update(double delta){
        animation.update();
        if(!isGrounded){
            posX += horizontalVelocity*delta;
            posY -= verticalVelocity*delta;
            verticalVelocity += gravity;
            if(posY > 300){
                posY = 300;
                isGrounded = true;
                verticalVelocity = 25;
                if(isWalking){
                    animation = walking;
                    animation.restart();
                }
                stop();
            }
        }
        posX += horizontalVelocity*delta;
    }
}