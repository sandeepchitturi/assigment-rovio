package com.rovio.plushmarket.processor.data;

import com.rovio.plushmarket.model.Plush;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;
@Service
public class PlushDataProcessor implements MarketDataProcessor<TreeMap<String, Plush>, List<Plush>> {
    @Override
    public TreeMap<String, BigDecimal> process(List<Plush> plushes) {
       return plushes.stream().collect(Collectors.toMap(Plush::getPlush,Plush::getPrice,(p1,p2)->p2,TreeMap::new));
    }
}
