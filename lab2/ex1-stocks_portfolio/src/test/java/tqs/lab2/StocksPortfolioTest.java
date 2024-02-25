package tqs.lab2;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(MockitoExtension.class)
public class StocksPortfolioTest {

    @InjectMocks
    private StocksPortfolio portfolio;

    @Mock
    private IStocksMarketService stockmarket;

    @Test
    public void testTotalValue() {

        Stock googleStock = new Stock("GOOGL", 2);
        Stock teslaStock = new Stock("TSLA", 2);
        portfolio.addStock(googleStock);
        portfolio.addStock(teslaStock);

        when(stockmarket.lookUpPrice("GOOGL")).thenReturn(1000.0);
        when(stockmarket.lookUpPrice("TSLA")).thenReturn(500.0);

        assertEquals(3000.0, portfolio.totalValue());

        verify(stockmarket, times(1)).lookUpPrice("GOOGL");
        verify(stockmarket, times(1)).lookUpPrice("TSLA");

    }

    @Test
    public void testTotalValueWithHamcrest() {

        Stock googleStock = new Stock("GOOGL", 2);
        Stock teslaStock = new Stock("TSLA", 2);
        portfolio.addStock(googleStock);
        portfolio.addStock(teslaStock);

        when(stockmarket.lookUpPrice("GOOGL")).thenReturn(1000.0);
        when(stockmarket.lookUpPrice("TSLA")).thenReturn(500.0);

        assertThat(portfolio.totalValue(), equalTo(3000.0));

        verify(stockmarket, times(1)).lookUpPrice("GOOGL");
        verify(stockmarket, times(1)).lookUpPrice("TSLA");

    }
}
