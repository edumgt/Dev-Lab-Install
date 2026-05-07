public class CarExample {
    public static void main(String[] args) {
        String action = "";
        Car myCar = new Car(); 
        myCar.setColor("빨강");
        action = myCar.drive(); 
        System.out.println(action);
        
        Car yourCar = new Car("파랑"); 
        // yourCar.color = "노랑";
        action = yourCar.drive(); 
        System.out.println(action);

        Car hisCar = new Car(); 
        action = hisCar.drive(); 
        System.out.println(action);
    }

}

class Car {
    private int id;       // 필드(Field)
    private String color; // 필드(Field)
    private int speed;    // 필드(Field)


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    // 1. 기본 생성자 (색상 없음)
    Car() {
        this.color = "무색";  // 기본값 지정
    }

    // 2. 매개변수 1개 생성자 (색상 지정)
    Car(String color) {
        this.color = color;
    }

    // 3. 매개변수 2개 생성자 (색상 + 추가 옵션)
    Car(String color, boolean isRunning) {
        this.color = color;
        if (isRunning) {
            System.out.println(color + " 자동차 시동이 걸렸습니다.");
        } else {
            System.out.println(color + " 자동차는 시동이 꺼져 있습니다.");
        }
    }

    String drive() {
        return color + " 색의 자동차가 running";
    }
}

