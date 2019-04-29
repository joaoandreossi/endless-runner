import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/*CLASSE: Responsável por gerar sprites (imagens individuais de cada frame de animação) a partir de um
          sprite sheet (uma imagem do tipo .png que contém todos os frames de animação).
          
          Os sprites são retornados dentro de um vetor de BufferedImage.*/
public class Sprite {

    private BufferedImage sheet;

    /*CONSTRUTOR: Recebe um parametro do tipo String que é o nome do arquivo de imagem no diretório raiz.
                  Em seguida, chama a função 'loadSheet' passando o nome obtido como parâmetro.*/
    public Sprite(String file){
        sheet = null;
        loadSheet(file);
    }

    /*MÉTODO: Tenta carregar a váriavel de classe 'sheet' com o sprite sheet de nome igual ao passado no
              construtor utilizando o função 'ImageIO.read' que recebe como parametro um tipo 'File'.
              
              Selecionamos um arquivo com o contrutor de 'File' e passamos o nome do arquivo obtido pelo
              construtor da função da nossa classe. Caso falhe em abrir o arquivo, captura o erro 'e'. */ 
    private void loadSheet(String file){
        try {
            sheet = ImageIO.read(new File("sheets/" + file + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*MÉTODO: Recebe como parâmetro o número de sprites em uma certa animação e o tamanho (x,y) de cada
              sprite (os sprites precisam ter o mesmo tamanho no sprite sheet).
              
              Retorna um vetor de BufferedImage que armazenará todos os sprites obtidos a partir do
              sprite sheet criado pela função 'loadSheet', sendo seu tamanho igual ao número de sprites na
              animação. 
              
              A separação das imagens é feita pela função 'getSubimage', que recebe como parâmetro pontos
              (x, y) que são as coordenadas do sprite no sprite sheet que se deseja extrair, e a altura
              e largura dos sprites. Como nesse caso todos os sprites estão na mesma linha, só variamos
              o parâmetro x e mantemos o y fixo, assim com o auxílio de um laço 'for', podemos percorrer
              todo o sprite sheet e armazenar os sprites individuais no vetor. Por isso a importância de
              se ter os sprites todos com o mesmo tamanho.*/
    public BufferedImage[] getSprites(int spriteNumber, int xTile, int yTile){
        BufferedImage[] sprites = new BufferedImage[spriteNumber];

        for(int i = 0; i < spriteNumber; i++){
            sprites[i] = sheet.getSubimage(i*xTile, 0*yTile, xTile, yTile);
        }

        return sprites;
    }
}