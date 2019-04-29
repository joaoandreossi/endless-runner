import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/*CLASSE: Responsável por controlar e executar as animações a partir de um vetor de sprites.*/
class Animation {

    /*VARIÁVEIS: int frameTime: indica a duração de cada frame
                 int frameTimeCounter: o contador da duração de cada frame
                 int currentFrame: o sprite atual da animação
                 int animationDirection: indica se a animação deve tocar normalmente(1) ou ao contrário(-1)
                 int totalFrames: o número total de sprites da animação
                 boolean stopped: indica se a animação está pausada
                 List<Frames> frames: a lista que armazena todos os sprites da animação e suas durações
                                      (os frames são instâncias da classe Frame) */
    private int frameTime;
    private int frameTimeCounter;
    private int currentFrame;
    private int animationDirection;
    private int totalFrames;
    private boolean stopped;
    private List<Frame> frames = new ArrayList<Frame>();

    /*CONSTRUTOR: Recebe como parâmetros um vetor de BufferedImage contendo os sprites que devem ser 
                  executados sequencialmente para constituir a animação, e um int que representa a 
                  velocidade da animação. 
                  
                  Cada frame e sua duração, é adicionado na lista local 'frames', que contém instâncias
                  da classe Frame (que armazena o frame individual e sua duração), em seguida, todas
                  as variáveis de classe são inicializadas.*/
    public Animation(BufferedImage[] frames, int frameTime){
        for(int i = 0; i < frames.length; i++){
            addFrame(frames[i], frameTime);
        }

        this.frameTime = frameTime;
        this.frameTimeCounter = 0;
        this.currentFrame = 0;
        this.animationDirection = 1;
        this.totalFrames = this.frames.size();
        this.stopped = true;
    }

    /*MÉTODO: Recebe um sprite do tipo BufferedImage e uma duração associada ao sprite.
              Caso a duração seja 0 ou negativa, ocorre uma excessão.
              
              O frame do parâmetro é adicionado no final da lista local 'frames' que é uma lista de 
              instâncias da classe Frame (que apenas mantém um frame e sua duração). Em seguida o número
              do frame atual é colocado em 0 (caso a animação estivesse em execução no momento)*/
    private void addFrame(BufferedImage frame, int duration){
        if(duration <= 0){
            System.err.println("Invalid duration: " + duration);
            throw new RuntimeException("Invalid duration: " + duration);
        }

        frames.add(new Frame(frame, duration));
        currentFrame = 0;
    }

    /*MÉTODO: Altera o valor do flag 'stopped' para 'false' para indicar que a animação deve ser executada,
              caso já esteja em 'false' ou se a lista de frames estiver vazia, a função não faz nada.*/
    public void start(){
        if(!stopped){
            return;
        }
        if(frames.size() == 0){
            return;
        }

        stopped = false;
    }

    /*MÉTODO: Altera o valor do flag 'stopped' para 'true' para indicar que a animação deve ser pausada
              no frame atual, caso a lista de frames esteja vazia, a função não faz nada.*/
    public void stop(){
        if(frames.size() == 0){
            return;
        }

        stopped = true;
    }

    /*MÉTODO: Altera o valor do flag 'stopped' para 'false' para indicar que a animação deve ser executada,
              e seta o frame atual para 0, de forma a resetar a animação. Caso a lista de frames estiver 
              vazia, a função não faz nada.*/
    public void restart(){
        if(frames.size() == 0){
            return;
        }

        stopped = false;
        currentFrame = 0;
    }

    /*MÉTODO: Altera o valor do flag 'stopped' para 'true' para indicar que a animação deve ser parada,
              e seta o frame atual para 0, de forma a deixar a animação parada no primeiro frame.*/
    public void reset(){
        this.stopped = true;
        this.frameTimeCounter = 0;
        this.currentFrame = 0;
    }

    /*MÉTODO: Retorna uma BufferedImage que contém o sprite do frame atual da animação.*/
    public BufferedImage getSprite(){
        return frames.get(currentFrame).getFrame();
    }

    /*MÉTODO: Faz o update da animação. Essa função deve ser colocada no loop principal do programa, assim
              ela será executada uma vez todo loop.
    
              Caso a animação não esteja parada (stopped = false), o contador de frame time é incrementado
              até que seu valor seja maior que o frame time (ou seja, o sprite ficou parado por um número
              de frames igual o frame time), quando o valor de frame time é atingido o frame atual
              passa para o próximo sprite da animação. Caso seja o último frame da lista, o frame atual
              é colocado no primeiro sprite novamente.*/
    public void update(){
        if(!stopped){
            frameTimeCounter++;
            
            if(frameTimeCounter > frameTime){
                frameTimeCounter = 0;
                currentFrame += animationDirection;

                if(currentFrame > totalFrames - 1){
                    currentFrame = 0;
                }
                else if(currentFrame < 0){
                    currentFrame = totalFrames - 1;
                }
            }
        }
    }
}