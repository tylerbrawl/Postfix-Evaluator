public class StackTooBigException extends Exception{
    public StackTooBigException(){
        super("ERROR: There are too many numbers and not enough operators.");
    }
}
