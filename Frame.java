import java.awt.image.BufferedImage;

/*CLASSE: Classe auxiliar a classe Animation. É responsável por associar um frame individual a uma 
          duração e implementa funções para setar e obter esses frames/durações.*/
public class Frame {
    private BufferedImage frame;
    private int duration;

    /*CONSTRUTOR: Recebe como parâmetros uma imagem do tipo BufferedImage e uma duração int.
                  Seta as variáveis de classe (frame, duration) ao mesmo valor passados no construtor
                  para serem manipuladas pelas outras funções.*/
    public Frame(BufferedImage frame, int duration){
        this.frame = frame;
        this.duration = duration;
    }

    /*MÉTODO: Retorna o frame do tipo BufferedImage dessa instância.*/
    public BufferedImage getFrame(){
        return frame;
    }

    /*MÉTODO: Seta o frame dessa instância para a BufferedImage passada como parâmetro.*/
    public void setFrame(BufferedImage frame){
        this.frame = frame;
    }

    /*MÉTODO: Retorna a duração int do frame dessa instância.*/
    public int getDuration(){
        return duration;
    }

    /*MÉTODO: Seta a duração do frame dessa instância para o int passado como parâmetro.*/
    public void setDuration(int duration){
        this.duration = duration;
    }
}