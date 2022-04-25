import React, { useEffect } from 'react';
import Box from '@mui/material/Box';
import MDTable from 'Components/UI/Atoms/MDTable/MDTable';
import Button from '@mui/material/Button';
import DeleteIcon from '@mui/icons-material/Delete';
import Stack from '@mui/material/Stack';
import CreateIcon from '@mui/icons-material/Create';
import { Link } from 'react-router-dom';
import axios from 'axios';

export default function Home() {
  useEffect(() => {
    // axios 요청 Build Project 리스트 목록
    // axios.get('/api/project').then((res) => {
    //   console.log(res.data);
    // });
  }, []);
  return (
    <Box sx={{ marginTop: 5 }}>
      <MDTable />
      <Stack mt={2} direction="row" justifyContent="flex-end" spacing={2}>
        <Button variant="outlined" startIcon={<DeleteIcon />}>
          Delete
        </Button>
        <Link to="/setting">
          <Button variant="contained" endIcon={<CreateIcon />}>
            Create
          </Button>
        </Link>
      </Stack>
    </Box>
  );
}
