package utils;


public class Vec2 {
    public float x;
    public float y;

    public Vec2(float x, float y){
        this.x = x;
        this.y = y;
    }

    public float length(){
        return (float) Math.sqrt(this.x * this.x + this.y * this.y);
    }

    public Vec2 normalize(){
        float length = this.length();
        return new Vec2(this.x / length, this.y / length);
    }

    @Override
    public String toString(){
        return "( " + this.x + ", " + this.y + ")";
    }
}
