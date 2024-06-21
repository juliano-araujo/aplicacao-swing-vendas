package vendas.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class FormatUtils {
	public static String toMonetaryString(BigDecimal value) {
		return FormatUtils.toMonetaryString(value, true);
	}
	
	public static String toMonetaryString(BigDecimal value, boolean withSign) {
		BigDecimal roundedValorParcial = value.setScale(2, RoundingMode.CEILING);
		
		var valorStr = roundedValorParcial.toString().replace('.', ',');
		
		var sb = new StringBuilder();
		
		if (withSign) {
			sb.append("R$ ");
		}
		
		sb.append(valorStr);
		
		return sb.toString();
	}
}
