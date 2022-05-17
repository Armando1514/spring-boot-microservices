package springframework.mmscbrewery.domain.strategies;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

import java.util.Objects;
import java.util.Random;
import java.util.UUID;

public class CustomGeneratorId implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        final String uuid = UUID.randomUUID().toString();
        final long longTimeRandom = System.nanoTime() + System.currentTimeMillis() + new Random().nextLong() + Objects.hash(object);
        final String timeHex = Long.toHexString(longTimeRandom);
        return UUID.fromString(timeHex.substring(8) + uuid.substring(8));
    }

}
