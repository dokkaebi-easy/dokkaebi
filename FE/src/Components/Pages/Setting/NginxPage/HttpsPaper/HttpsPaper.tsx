import React, { useState } from 'react';
import Box from '@mui/material/Box';
import Paper from '@mui/material/Paper';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import TextField from '@mui/material/TextField';
import Checkbox from '@mui/material/Checkbox';
import FormHelperText from '@mui/material/FormHelperText';
import Stack from '@mui/material/Stack';
import { useSettingStore } from 'Components/Store/SettingStore/SettingStore';

export default function HttpsPaper() {
  const nginxConfig = useSettingStore((state) => state.nginxConfig);

  const [https, setHttps] = useState(nginxConfig.https);

  const [sslCertificate, setSslCertificate] = useState(
    nginxConfig.httpsOption.sslCertificate,
  );
  const [sslCertificateKey, setSslCertificateKey] = useState(
    nginxConfig.httpsOption.sslCertificateKey,
  );
  const [sslPath, setSslPath] = useState(nginxConfig.httpsOption.sslPath);

  const handleCheckBoxChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setHttps(event.target.checked);
    nginxConfig.https = event.target.checked;
    if (!event.target.checked) {
      setSslCertificate('');
      nginxConfig.httpsOption.sslCertificate = '';
      setSslCertificateKey('');
      nginxConfig.httpsOption.sslCertificateKey = '';
      setSslPath('');
      nginxConfig.httpsOption.sslPath = '';
    }
  };

  const handleSslCertificateChange = (
    event: React.ChangeEvent<HTMLInputElement>,
  ) => {
    setSslCertificate(event.target.value);
    nginxConfig.httpsOption.sslCertificate = event.target.value;
  };

  const handleSslCertificateKeyChange = (
    event: React.ChangeEvent<HTMLInputElement>,
  ) => {
    setSslCertificateKey(event.target.value);
    nginxConfig.httpsOption.sslCertificateKey = event.target.value;
  };

  const handleSslPathChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setSslPath(event.target.value);
    nginxConfig.httpsOption.sslPath = event.target.value;
  };

  return (
    <Box>
      <Box position="relative" sx={{ top: 20, left: 10 }}>
        <Paper
          sx={{
            padding: 1,
            textAlign: 'center',
            width: 120,
            color: ' white',
            background: 'linear-gradient(195deg, #666, #191919)',
          }}
        >
          HTTPS
        </Paper>
      </Box>
      <Box>
        <Paper sx={{ padding: 3 }}>
          <Grid container spacing={1} mt={1}>
            <Grid item xs={12} sx={{ margin: 'auto auto' }}>
              <FormHelperText
                id="component-helper-text"
                style={{
                  color: 'red',
                  fontWeight: 'bold',
                }}
              >
                ※ 사용자 가이드 참고
              </FormHelperText>
              <Stack direction="row" alignItems="center" spacing={2}>
                <Typography>HTTPS</Typography>
                <Checkbox checked={https} onChange={handleCheckBoxChange} />
                <FormHelperText id="component-helper-text">
                  (※ 체크해야 HTTPS 기능이 활성화됩니다.)
                </FormHelperText>
              </Stack>
            </Grid>
            <Grid item xs={12}>
              <Typography>SSL Certificate</Typography>
              <TextField
                fullWidth
                disabled={!https}
                label="SSL Certificate"
                variant="outlined"
                size="small"
                sx={{ my: 1 }}
                placeholder="SSL Certificate"
                defaultValue={sslCertificate}
                onChange={handleSslCertificateChange}
              />
            </Grid>
            <Grid item xs={12}>
              <Typography>SSL Certificate Key</Typography>
              <TextField
                fullWidth
                disabled={!https}
                label="SSL Certificate Key"
                variant="outlined"
                size="small"
                sx={{ my: 1 }}
                placeholder="SSL Certificate Key"
                defaultValue={sslCertificateKey}
                onChange={handleSslCertificateKeyChange}
              />
            </Grid>
            <Grid item xs={12}>
              <Typography>SSL 경로</Typography>
              <TextField
                fullWidth
                disabled={!https}
                label="SSL Path"
                variant="outlined"
                size="small"
                sx={{ my: 1 }}
                placeholder="SSL Path"
                defaultValue={sslPath}
                onChange={handleSslPathChange}
              />
            </Grid>
          </Grid>
        </Paper>
      </Box>
    </Box>
  );
}
