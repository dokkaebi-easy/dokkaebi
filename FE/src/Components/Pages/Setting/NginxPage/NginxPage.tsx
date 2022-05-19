import Box from '@mui/material/Box';
import Paper from '@mui/material/Paper';
import Typography from '@mui/material/Typography';
import DomainPaper from './DomainPaper/DomainPaper';
import ProxypassPaper from './ProxypassPaper/ProxypassPaper';
import HttpsPaper from './HttpsPaper/HttpsPaper';

export default function NginxPage() {
  return (
    <Box>
      <Box position="relative" sx={{ top: 20, left: 10 }}>
        <Paper
          sx={{
            padding: 1,
            textAlign: 'center',
            width: 450,
            color: ' white',
            background: 'linear-gradient(195deg, #666, #191919)',
          }}
        >
          <Typography variant="h5" component="h2">
            NGINX (front with nginx) setting
          </Typography>
        </Paper>
      </Box>
      <Box>
        <Paper sx={{ padding: 3 }}>
          <DomainPaper />
          <ProxypassPaper />
          <HttpsPaper />
        </Paper>
      </Box>
    </Box>
  );
}
