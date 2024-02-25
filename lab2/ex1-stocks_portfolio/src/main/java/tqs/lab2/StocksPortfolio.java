package tqs.lab2;
import java.util.List;
import java.util.ArrayList;

public class StocksPortfolio {

    private IStocksMarketService stockmarket;
    private List<Stock> stocks;


    public StocksPortfolio(IStocksMarketService stockmarket){
        this.stockmarket = stockmarket;
        this.stocks = new ArrayList<Stock>();
    }

    public void addStock(Stock stock){
        stocks.add(stock);
    }

    public double totalValue(){
        double total = 0;
        for (Stock stock : stocks){
            total += stockmarket.lookUpPrice(stock.getLabel()) * stock.getQuantity();
        }
        return total;
    }





    
}
