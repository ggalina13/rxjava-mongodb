package model;

public enum Currency {
    dollars,
    euro,
    rubles;
    public double dollarExchangeRate(){
        switch (this){
            case dollars:
                return 1;
            case euro:
                return 0.014;
            case rubles:
                return 73;
        }
        return 0;
    }
}
