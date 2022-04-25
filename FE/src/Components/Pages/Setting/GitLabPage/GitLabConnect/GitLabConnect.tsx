import React, { useState } from 'react';
import Box from '@mui/material/Box';
import Paper from '@mui/material/Paper';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import TextField from '@mui/material/TextField';
import SelectItem from 'Components/UI/Atoms/SelectItem/SelectItem';
import Button from '@mui/material/Button';
import AddIcon from '@mui/icons-material/Add';
import Stack from '@mui/material/Stack';
import AutorenewIcon from '@mui/icons-material/Autorenew';
import { v4 as uuid } from 'uuid';
import ConnetctModal from 'Components/Pages/Setting/GitLabPage/ConnetctModal/ConnetctModal';

export default function GitLabConnect() {
  const [credentials, setCredentials] = useState([]);
  const [secretToken, setSecretToken] = useState('');
  const [open, setOpen] = useState(false);

  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);
  const handleOnClick = () => setSecretToken(uuid());

  return (
    <Box my={3}>
      <Box position="relative" sx={{ top: 20, left: 10 }}>
        <Paper sx={{ padding: 1, textAlign: 'center', width: 150 }}>
          Connect
        </Paper>
      </Box>
      <Box>
        <Paper sx={{ padding: 3 }}>
          <Grid container spacing={2}>
            <Grid item xs={2} sx={{ margin: 'auto auto' }}>
              <Typography>name</Typography>
            </Grid>
            <Grid item xs={10}>
              <TextField
                fullWidth
                id="outlined-basic"
                label="name"
                variant="outlined"
                size="small"
                sx={{ my: 1 }}
                placeholder="name"
              />
            </Grid>
            <Grid item xs={2} sx={{ marginY: 'auto' }}>
              <Typography>Host URL</Typography>
            </Grid>
            <Grid item xs={10}>
              <TextField
                fullWidth
                id="outlined-basic"
                label="name"
                variant="outlined"
                size="small"
                sx={{ my: 1 }}
                placeholder="name"
              />
            </Grid>
            <Grid item xs={2} sx={{ marginY: 'auto' }}>
              <Typography>Credentials</Typography>
            </Grid>
            <Grid item xs={10}>
              <Stack direction="row" spacing={2} alignItems="center">
                <SelectItem label="Credentials" Items={credentials} />
                <Button
                  variant="outlined"
                  startIcon={<AddIcon />}
                  size="small"
                  onClick={handleOpen}
                >
                  Add
                </Button>
                <ConnetctModal open={open} Close={handleClose} />
              </Stack>
            </Grid>
            <Grid item xs={2} sx={{ marginY: 'auto' }}>
              <Typography>Secret Token</Typography>
            </Grid>
            <Grid item xs={6}>
              <TextField
                fullWidth
                disabled
                id="outlined-basic"
                variant="outlined"
                size="small"
                value={secretToken}
                sx={{ my: 1 }}
              />
            </Grid>
            <Grid item xs={4} sx={{ my: 'auto' }}>
              <Stack direction="row" spacing={2}>
                <Button
                  variant="outlined"
                  startIcon={<AddIcon />}
                  size="small"
                  onClick={handleOnClick}
                >
                  Create
                </Button>
                <Button
                  variant="outlined"
                  startIcon={<AutorenewIcon />}
                  size="small"
                >
                  test Connection
                </Button>
              </Stack>
            </Grid>
          </Grid>
        </Paper>
      </Box>
    </Box>
  );
}
