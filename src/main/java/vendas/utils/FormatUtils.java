package vendas.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class FormatUtils {
	public static String toMonetaryString(BigDecimal value) {
		BigDecimal roundedValorParcial = value.setScale(2, RoundingMode.CEILING);
		
		var valorStr = roundedValorParcial.toString().replace('.', ',');
		
		return "R$ " + valorStr;
	}
}
