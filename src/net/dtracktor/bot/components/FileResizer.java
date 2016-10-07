package net.dtracktor.bot.components;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import java.io.File;


import java.io.IOException;

import javax.imageio.ImageIO;

import net.dtracktor.bot.Start;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class FileResizer implements Processor {
    
            BotConfigurator configurator;
            volatile int counter = 0;
            private static final int IMG_WIDTH  = 640;
            private static final int IMG_HEIGHT = 640;

            public static void main(String [] args){

            try {

                    BufferedImage originalImage = ImageIO.read(new File("./images/chesstop.jpg"));
                    int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();

                    BufferedImage resizeImageJpg = resizeImage(originalImage, type);
                    ImageIO.write(resizeImageJpg, "jpg", new File("./images/chesstop1.jpg"));

                    BufferedImage resizeImagePng = resizeImage(originalImage, type);
                    ImageIO.write(resizeImagePng, "png", new File("./images/chesstop2.png"));

                    BufferedImage resizeImageHintJpg = resizeImageWithHint(originalImage, type);
                    ImageIO.write(resizeImageHintJpg, "jpg", new File("./images/chesstop1h.jpg"));

                    BufferedImage resizeImageHintPng = resizeImageWithHint(originalImage, type);
                    ImageIO.write(resizeImageHintPng, "png", new File("./images/chesstop2h.png"));

            } catch(IOException e){
                    System.out.println(e.getMessage());
            }

        }

        private static BufferedImage resizeImage(BufferedImage originalImage, int type){
            BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
            Graphics2D g = resizedImage.createGraphics();
            g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
            g.dispose();

            return resizedImage;
        }

        private static BufferedImage resizeImageWithHint(BufferedImage originalImage, int type){

            BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
            Graphics2D g = resizedImage.createGraphics();
            g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
            g.dispose();
            g.setComposite(AlphaComposite.Src);

            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
            RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.setRenderingHint(RenderingHints.KEY_RENDERING,
            RenderingHints.VALUE_RENDER_QUALITY);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);

            return resizedImage;
        }
    



    public void setConfigurator(BotConfigurator configurator) {
        this.configurator = configurator;
    }

    public BotConfigurator getConfigurator() {
        return configurator;
    }

    public FileResizer() {
        super();
    }
    
    public void process (Exchange ex) throws Exception {
           
           
        counter++;
        int exitcode = 0;
        if (Start.n>0 && counter>Start.n) {
            Start.done = true;
            throw new Exception ();
        }



        String qs = ex.getIn().getBody(String.class);
        Start.log (qs);

        String homeDir  = configurator.getProperty("HOME_DIR","./");
        File fs = new File (qs);
        if (fs.exists()) {
            
            String fsn = fs.getName();
            int ts = fsn.lastIndexOf(".");
            fsn = fsn.substring(0,ts)+".jpg"; 
            File fso = new File (homeDir+File.separator+"image_resized"+File.separator+fsn);
            fso.mkdirs();
            BufferedImage originalImage = ImageIO.read(fs);
            int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();

            BufferedImage resizeImageJpg = resizeImage(originalImage, type);
            ImageIO.write(resizeImageJpg, "jpg", fso);
            ex.getOut().setBody(fso.getAbsolutePath());
            fs.delete();
        
        } else {
            
            ex.getOut().setBody(qs);
            exitcode=1;
            
        }
        ex.getOut().setHeader("exitcode", exitcode);

           
    }
}
