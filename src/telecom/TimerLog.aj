package telecom;

public aspect TimerLog {

    after(Timer t) returning () : target(t) && call(* Timer.start())  {
      System.err.println("Timer started: " + t.startTime);
    }

    //fault injected - call(* Timer.stop()) switched with call(* Timer.start())
    
    
    after(Timer t) returning () : target(t) && call(* Timer.stop()) {
      System.err.println("Timer stopped: " + t.stopTime);
    }
}
