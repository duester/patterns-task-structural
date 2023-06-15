package task.structural.dependency.fourier;

import java.util.Map;

/**
 * Анализатор частотного спектра
 */
public interface FourierTransformer {

    /**
     * анализирует частотный спектр и возвращает гистограмму частот
     * (частота - количество вхождений)
     */
    public Map<Double, Integer> getFrequencies(byte[] data);
}
