/**
 * Copyright (c) The openTCS Authors.
 *
 * This program is free software and subject to the MIT license. (For details,
 * see the licensing information (LICENSE.txt) you should have received with
 * this copy of the software.)
 */
package org.opentcs.kernel.vehicles;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import org.opentcs.components.kernel.services.InternalVehicleService;
import org.opentcs.data.model.Vehicle;
import org.opentcs.drivers.vehicle.VehicleCommAdapter;

/**
 * Tests for the {@link StandardVehicleManagerPoolTest}.
 *
 * @author Philipp Seifert (Fraunhofer IML)
 * @author Stefan Walter (Fraunhofer IML)
 */
public class StandardVehicleManagerPoolTest {

  /**
   * A name for a vehicle.
   */
  private static final String A_VEHICLE_NAME = "MyVehicle";
  /**
   * An name for a vehicle that does not exist.
   */
  private static final String UNKNOWN_VEHICLE_NAME = "SomeUnknownVehicle";
  /**
   * The (mocked) vehicle service.
   */
  private InternalVehicleService vehicleService;
  /**
   * A (mocked) communication adapter.
   */
  private VehicleCommAdapter commAdapter;
  /**
   * The VehicleManagerpool we're testing.
   */
  private LocalVehicleControllerPool vehManagerPool;

  @BeforeEach
  public void setUp() {
    vehicleService = mock(InternalVehicleService.class);
    commAdapter = mock(VehicleCommAdapter.class);
    vehManagerPool = new DefaultVehicleControllerPool(vehicleService,
                                                      new MockedVehicleManagerFactory());
  }

  @Test
  public void testThrowsNPEIfVehicleNameIsNull() {
    assertThrows(NullPointerException.class,
                 () -> vehManagerPool.attachVehicleController(null, commAdapter));
  }

  @Test
  public void testThrowsNPEIfCommAdapterIsNull() {
    assertThrows(NullPointerException.class,
                 () -> vehManagerPool.attachVehicleController(A_VEHICLE_NAME, null));
  }

  @Test
  public void testThrowsExceptionForUnknownVehicleName() {
    assertThrows(IllegalArgumentException.class,
                 () -> vehManagerPool.attachVehicleController(UNKNOWN_VEHICLE_NAME, commAdapter));
  }

  @Test
  public void testThrowsNPEIfDetachingNullVehicleName() {
    assertThrows(NullPointerException.class,
                 () -> vehManagerPool.detachVehicleController(null));
  }

  /**
   * A factory delivering vehicle manager mocks.
   */
  private static class MockedVehicleManagerFactory
      implements VehicleControllerFactory {

    @Override
    public DefaultVehicleController createVehicleController(Vehicle vehicle,
                                                            VehicleCommAdapter commAdapter) {
      return mock(DefaultVehicleController.class);
    }
  }
}
