package io.pivotal.workshop;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

// In order to use Spring caching, we need Spring context
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes={SpringBootTddApplication.class})
public class CarServiceCachingTests {

    @Autowired
    private CarService carService;

    @MockBean
    private CarRepository carRepository;

    @Test
    public void getCarDetails_should_use_caching_when_called_twice() throws Exception {

        given(carRepository.findByName(anyString())).willReturn(new Car("prius", "hybrid"));

        carService.getCarDetails("prius");
        carService.getCarDetails("prius");

        verify(carRepository, times(1)).findByName(anyString());
    }
}
