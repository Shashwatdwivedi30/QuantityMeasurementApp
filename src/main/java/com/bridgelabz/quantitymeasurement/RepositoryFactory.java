package com.bridgelabz.quantitymeasurement;

/**
 * Factory to switch between cache and database repositories without changing
 * service or controller code.
 */
public final class RepositoryFactory {

    private RepositoryFactory() {
    }

    public static IQuantityMeasurementRepository createRepository() {
        String repositoryType = DatabaseConfig.getRepositoryType();
        if ("cache".equalsIgnoreCase(repositoryType)) {
            return QuantityMeasurementCacheRepository.getInstance();
        }
        return new QuantityMeasurementDatabaseRepository();
    }
}
