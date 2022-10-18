/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opticalbarcodereaders;

/**
 *
 * @author Ray-L
 */
public class Opticalbarcodereaders {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {      
      String[] sImageIn =
      {
            "                                          ",
            "                                          ",
            "* * * * * * * * * * * * * * * * * * *     ",
            "*                                    *    ",
            "**** *** **   ***** ****   *********      ",
            "* ************ ************ **********    ",
            "** *      *    *  * * *         * *       ",
            "***   *  *           * **    *      **    ",
            "* ** * *  *   * * * **  *   ***   ***     ",
            "* *           **    *****  *   **   **    ",
            "****  *  * *  * **  ** *   ** *  * *      ",
            "**************************************    ",
            "                                          ",
            "                                          ",
            "                                          ",
            "                                          "

      };
     
      BarcodeImage bc = new BarcodeImage(sImageIn);
      Datamatrix dm = new Datamatrix(bc);
     
      // Secret message
      dm.translateImageToText();
      dm.displayTextToConsole();
      dm.displayImageToConsole();  
      
      // Create your own message
      dm.readText("What a great resume builder this is!");
      dm.generateImageFromText();
      dm.displayTextToConsole();
      dm.displayImageToConsole();
   }       
}

class BarcodeImage implements Cloneable
{
   public static final int MAX_HEIGHT = 30;
   public static final int MAX_WIDTH = 65;
   private boolean[][] imageData;
   
   //Default constructor
   public BarcodeImage()
   {
      imageData = new boolean[MAX_HEIGHT][MAX_WIDTH];
      
      //Setting each array value to false
      for(int i = 0; i < MAX_HEIGHT; i++)
      {
         for(int j = 0; j < MAX_WIDTH; j++) imageData[i][j] = false;
      }
   }
   
   //Converts 1D array of Strings to the internal 2D array of booleans.
   public BarcodeImage(String[] strData)
   {
      this();
      if(checkSize(strData))  //Checks if strData is valid
      {
         for(int i = 0; i < strData.length; i++)
         {
            //System.out.print("[");
            for(int j = 0; j < strData[i].length(); j++)
            {               
               if(strData[i].charAt(j) == '*')
               {
                  //System.out.print("true, ");                  
                  setPixel(i, j, true);
               }
               //else setPixel(MAX_HEIGHT - (strData.length - i), j, false);
               else 
               {
                  //System.out.print("false, ");
                  setPixel(i, j, false);                  
               }
            }
            //System.out.println("]");
         }
      }
   }
   
   //Accessor for Pixel
   public boolean getPixel(int row, int col)
   {
      return imageData[row][col];
   }
   
   //Mutator for Pixel
   public boolean setPixel(int row, int col, boolean value)
   {
      if(row >= MAX_HEIGHT || col >= MAX_WIDTH) return false;
      else
      {
         imageData[row][col] = value;
         return true;
      }      
   }
   
   private boolean checkSize(String[] data)
   {
      if(data == null) return false;
      if(data.length >= MAX_HEIGHT) return false;
      return true;
   }
   
   //Overrides the method of the name in Cloneable interface
   protected Object clone() throws CloneNotSupportedException
   {
      BarcodeImage bci = (BarcodeImage)super.clone();
      for(int i = 0; i < MAX_HEIGHT; i++)
      {
         //System.out.print("[");
         for(int j = 0; j < MAX_WIDTH; j++) 
         {
            //System.out.print(this.getPixel(i, j) + ", ");
            bci.imageData[i][j] = this.getPixel(i, j);            
         }
         //System.out.println("]");
      }
      return bci;
   }
}

interface BarcodeIO {
    // accepts some image, represented as a BarcodeImage object to be described
    // below, and stores a copy of this image
    public boolean scan(BarcodeImage bc);

    // accepts a text string to be eventually encoded in an image.
    // no translation is done here
    public boolean readText(String text);

    // looks at the internal text stored in the implementing class and produces a
    // companion BarcodeImage
    public boolean generateImageFromText();

    // looks at the internal image stored in the implementing class, and produces a
    // companion text string, internally
    public boolean translateImageToText();

    // prints out the text string to the console
    public void displayTextToConsole();

    // prints out the image to the console
    public void displayImageToConsole();
}


class Datamatrix implements BarcodeIO {
    // data
    public static final char BLACK_CHAR = '*';
    public static final char WHITE_CHAR = ' ';
    private BarcodeImage image;
    private String text;
    private int actualWidth;
    private int actualHeight;

    // constructs an empty, but non-null, image and text value
    public Datamatrix() {
        image = new BarcodeImage();
        text = "";
        actualWidth = 0;
        actualHeight = 0;
    }

    // sets the image but leaves the text at its default value
    public Datamatrix(BarcodeImage image) {
        scan(image);
        text = "";

    }

    // sets the text but leaves the image at its default value
    public Datamatrix(String text) {
        readText(text);
        image = new BarcodeImage();
        this.actualWidth = 0;
        this.actualHeight = 0;
    }

    // mutator for text
    public boolean readText(String text) {
        this.text = text;
        if (text == "" || text.length() > BarcodeImage.MAX_WIDTH - 2) {
            return false;
        } else {
            this.text = text;
            return true;
        }
    }

    // mutator for image, and then mutates actualWidth and actualHeight
    // Besides calling the clone() method of the BarcodeImage class,
    // this method calls cleanImage() and then
    // set the actualWidth and actualHeight
    public boolean scan(BarcodeImage image) {
        // should deal with the CloneNotSupportedException by embedding the clone() call
        // within a try/catch block
        try {
            this.image = (BarcodeImage) image.clone();
        } catch (CloneNotSupportedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        cleanImage();
        actualWidth = computeSignalWidth();
        actualHeight = computeSignalHeight();
        return true;
    }

    // accessors for actualWidth and actualHeight
    public int getActualWidth() {
        return actualWidth;
    }

    public int getActualHeight() {
        return actualHeight;
    }

    // scans the larger boolean array's pixels from left to right to determine
    // actual width
    private int computeSignalWidth() {
        int longestWidth = 0;
        // indents longestWidth until finding white pixel
        // using lower-left corner = j is 0
        //System.out.print("[");
        for(int j = 0; j < BarcodeImage.MAX_WIDTH; j++)
        {
           //System.out.print(image.getPixel(BarcodeImage.MAX_HEIGHT - 1, j) + ", ");
           if(image.getPixel(BarcodeImage.MAX_HEIGHT - 1, j) == true)
           {
              longestWidth++;
           }
        }
        //System.out.println("]");
        /*for (int j = longestWidth; image.getPixel(BarcodeImage.MAX_HEIGHT - 1, j) == true; j++) {
            longestWidth++;
        }*/
        //System.out.println("SIGNAL WIDTH: " + longestWidth);
        return longestWidth - 1;
        
    }

    // scans the
    private int computeSignalHeight() {
        int longestHeight = 0;
        // indents longestHeight until finding white pixel
        // using lower-left corner = i is MAX_HEIGHT-1
        for (int i = BarcodeImage.MAX_HEIGHT - 1; image.getPixel(i, 0) == true; i--) {
            longestHeight++;
        }
        return longestHeight - 2;
    }

    private void cleanImage() {
        // first, find location of the start of the spine
        int spineRow = BarcodeImage.MAX_HEIGHT;
        int spineColumn = BarcodeImage.MAX_WIDTH;
        // starts checking from bottom-left corner, checking upward by the column
        for (int j = 0; j < BarcodeImage.MAX_WIDTH; j++) {
            for (int i = BarcodeImage.MAX_HEIGHT - 1; i >= 0; i--) {
                // if found the corner of the spine, set spine coordinates and stop loop
                if (image.getPixel(i, j) == true) {    
                    //System.out.println("true");
                    spineRow = i;
                    spineColumn = j;
                    break;
                }
            }
            if(spineRow != BarcodeImage.MAX_HEIGHT) break; 
        }
        //System.out.println("SPINEROW: " + spineRow);
        //System.out.println("SPINECOLUMN: " + spineColumn);
        // forloop that clones image's pixels onto a new BarcodeImage with new position,
        // starting from bottom-left of image's spine
        BarcodeImage fixedImage = new BarcodeImage();
        for (int i = spineRow; i >= 0; i--) {
            for (int j = spineColumn; j < BarcodeImage.MAX_WIDTH; j++) {
                // row = decreasingRowVariable + constantRowDifference
                // column = increasingColumnVariable - constantColumnDifference
                fixedImage.setPixel(i + (BarcodeImage.MAX_HEIGHT - spineRow - 1), j - spineColumn, image.getPixel(i, j));
            }
        }
        // replace image with the newly-made BarCodeImage
        image = fixedImage;

    }

 // displays only the relevant portion of the image
   public void displayImageToConsole() {
      // "|--------------------|"
      System.out.print("|");
      for (int j = 0; j < actualWidth + 2; j++) {
         System.out.print("-");
      }
      System.out.println("|");

      // "|* * * * * * * *|"
      for (int i = BarcodeImage.MAX_HEIGHT - actualHeight - 2; i < BarcodeImage.MAX_HEIGHT; i++) {
         System.out.print("|");
         for (int j = 0; j < actualWidth + 1; j++) {
            //System.out.print(image.getPixel(i, j));
            if(image.getPixel(i, j) == true) {
               System.out.print(BLACK_CHAR);
            }else {
               System.out.print(WHITE_CHAR);
            }
         }
         System.out.println("|");
      }

      // "|--------------------|"
      System.out.print("|");
      for (int j = 0; j < this.actualWidth + 2; j++) {
         System.out.print("-");
      }
      System.out.println("|");
   }

    // the methods from the BarcodeIO inferface
    @Override
    public boolean generateImageFromText() {
       actualWidth = this.text.length() + 2;
       actualHeight = 10;
       this.cleanImage();
       
       //Set borders
       for(int i = 0; i < actualWidth; i++)
       {
          this.image.setPixel(image.MAX_HEIGHT - 1, i, true);
          if(i % 2 == 0) this.image.setPixel(image.MAX_HEIGHT - this.actualHeight, i, true);
       }
       for(int i = 0; i < actualHeight; i++)
       {
           this.image.setPixel(image.MAX_HEIGHT - this.actualHeight + i, 0, true);
           if(i % 2 == 0) this.image.setPixel(image.MAX_HEIGHT - this.actualHeight + i, this.actualWidth - 1, true);
       }
       
       //Translates the string value into a barcode
       for(int i = 0; i < text.length(); i++)
       {
           writeCharToCol(i, (int) text.charAt(i));
       }
       
       return true;
    }
    
    //Translates the text value into barcode.
    private boolean writeCharToCol(int col, int textTranslation)
    {
        int translate = 49;
        String binData = Integer.toString(textTranslation, 2);
        for(int i = 0; i < binData.length(); i++)
        {
            if((int) (binData.charAt(i)) == translate)
            {
                this.image.setPixel((this.image.MAX_HEIGHT - this.actualHeight - 1 +
                    (this.actualHeight - binData.length()) + i), col + 1, true);
            }
        }
        return true;
    }

    @Override    
    public boolean translateImageToText()
    {
       text = "";
       int value = 0;   
       int temp = 0;
       for(int col = 1; col < actualWidth; col++)
       {
          //System.out.print("[");
          for(int row = BarcodeImage.MAX_HEIGHT - 2; row >= BarcodeImage.MAX_HEIGHT - actualHeight - 1; row--)
          {
             //Get value of where the star is 
             //System.out.print(image.getPixel(row, col) + ", ");
             
             if(image.getPixel(row, col) == true)
             {
                value += (int)Math.pow(2, temp);
                //System.out.println(value);
             }
             temp++;             
          }
          //System.out.println("]");
          //Change int to char
          //System.out.println(value);
          char currChar = (char)value;
          //System.out.println(currChar);
          //Concatenate char to text
          text = text + currChar;
          value = 0;
          temp = 0;
       }
       //System.out.println(text);        
       if (this.text == "" || text == null) {
           return false;
       } else {
           return true;
       }
       
    }
    
    

    @Override
    public void displayTextToConsole() {
        // TODO Auto-generated method stub
        System.out.println(text);
    }
}
