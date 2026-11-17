
UPDATE oauth2_registered_client
SET token_settings = '{
  "@class": "java.util.Collections$UnmodifiableMap",
  "settings.token.access-token-format": {
    "@class": "org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat",
    "value": "reference"
  },
  "settings.token.authorization-code-time-to-live": [
    "java.time.Duration",
    300.000000000
  ],
  "settings.token.access-token-time-to-live": [
    "java.time.Duration",
    300.000000000
  ],
  "settings.token.refresh-token-time-to-live": [
    "java.time.Duration",
    3600.000000000
  ],
  "settings.token.reuse-refresh-tokens": true
}'
WHERE client_id = 'react-spa-client';
