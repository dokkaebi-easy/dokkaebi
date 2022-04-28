import React, { useState } from 'react';
import Typography from '@mui/material/Typography';
import Modal from '@mui/material/Modal';
import Box from '@mui/material/Box';
import Paper from '@mui/material/Paper';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import Stack from '@mui/material/Stack';
import axios from 'axios';
import ResponseIdNameData, {
  ResponseIdName,
} from 'Components/MDClass/ResponseIdNameData/ResponseIdNameData';

interface modalSwitch {
  open: boolean;
  Close: () => void;
  Change: (data: ResponseIdName[]) => void;
}

const style = {
  position: 'absolute' as const,
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  width: 600,
  bgcolor: 'background.paper',
  borderRadius: 10,
  boxShadow: 24,
  p: 4,
};
export default function RepositoryModal({ open, Close, Change }: modalSwitch) {
  const [id, setId] = useState('');
  const [passWord, setPassWord] = useState('');
  const [userName, setUserName] = useState('');

  const handleIdChange = (event: any) => {
    setId(event.target.value);
  };

  const handlePassWordChange = (event: any) => {
    setPassWord(event.target.value);
  };
  const handleUserNameChange = (event: any) => {
    setUserName(event.target.value);
  };

  const handleSaveClick = () => {
    const data = {
      email: id,
      password: passWord,
      username: userName,
    };

    axios
      .post('/api/git/account', data)
      .then((res) => {
        Change(res.data);
      })
      .catch((err) => {
        console.log(err);
      });
  };

  const handleCloseClick = () => {
    Close();
  };
  return (
    <Modal
      open={open}
      onClose={Close}
      aria-labelledby="modal-modal-title"
      aria-describedby="modal-modal-description"
    >
      <Box sx={style}>
        <Box position="relative" sx={{ top: 20, left: 10 }}>
          <Paper sx={{ padding: 1, textAlign: 'center', width: 250 }}>
            <Typography id="modal-modal-title" variant="h6" component="h2">
              Repository Credentials
            </Typography>
          </Paper>
        </Box>
        <Paper sx={{ padding: 3 }}>
          <Typography id="modal-modal-description" sx={{ my: 2 }}>
            UserName with Password
          </Typography>
          <Typography>ID</Typography>
          <TextField
            fullWidth
            id="outlined-basic"
            label="ID"
            variant="outlined"
            size="small"
            sx={{ my: 1 }}
            placeholder="ID"
            onChange={handleIdChange}
          />
          <Typography>Password</Typography>
          <TextField
            fullWidth
            id="outlined-basic"
            label="Password"
            variant="outlined"
            size="small"
            sx={{ my: 1 }}
            placeholder="Password"
            onChange={handlePassWordChange}
          />
          <Typography>UserName</Typography>
          <TextField
            fullWidth
            id="outlined-basic"
            label="UserName"
            variant="outlined"
            size="small"
            sx={{ my: 1 }}
            placeholder="UserName"
            onChange={handleUserNameChange}
          />
          <Stack mt={3} direction="row" justifyContent="flex-end" spacing={2}>
            <Button variant="outlined" size="small" onClick={handleSaveClick}>
              Save
            </Button>
            <Button variant="outlined" size="small" onClick={handleCloseClick}>
              Cancel
            </Button>
          </Stack>
        </Paper>
      </Box>
    </Modal>
  );
}
