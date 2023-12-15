package mmitina.bank.DAO;

import lombok.RequiredArgsConstructor;
import mmitina.bank.entities.Client;
import mmitina.bank.repositories.ClientRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientDAO {
    private final ClientRepository clientRepository;
    public void save(Client client) {
        clientRepository.save(client);
    }
    public Client findById(int clientId) {
        return clientRepository.findById(clientId).orElse(null);
    }
    public Client findByEmail(String email) {
        return clientRepository.findByEmail(email);
    }
    public void deleteById(int clientId) {
        clientRepository.deleteById(clientId);
    }
}
