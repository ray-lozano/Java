package opticalbarcodereaders;

public class BarcodeImage implements Cloneable
{
   public static final int MAX_HEIGHT = 30;
   public static final int MAX_WIDTH = 64;
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
            for(int j = 0; j < strData.length; j++)
            {
               if(strData[i].charAt(j) == '*')
               {
                  setPixel(MAX_HEIGHT - (strData.length - i), j, true);
               }
               else setPixel(MAX_HEIGHT - (strData.length - i), j, false);
            }
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
         for(int j = 0; j < MAX_WIDTH; j++) 
         {
            bci.imageData[i][j] = this.getPixel(i, j);            
         }
      }
      return bci;
   }
   
}
