import React from "react";
import { Button, HStack, Text } from "@chakra-ui/react";

const Pagination = ({ currentPage, totalPages, onPageChange }) => {
  const handlePrevPage = () => {
    if (currentPage > 1) {
      onPageChange(currentPage - 1);
    }
  };

  const handleNextPage = () => {
    if (currentPage < totalPages - 1) {
      onPageChange(currentPage + 1);
    }
  };

  return (
    <HStack justify="center" mt={4}>
      <Button onClick={handlePrevPage} disabled={currentPage === 1}>
        Предходна
      </Button>
      <Text>
        {currentPage + 1} од {totalPages}
      </Text>
      <Button onClick={handleNextPage} disabled={currentPage === totalPages}>
        Следна
      </Button>
    </HStack>
  );
};

export default Pagination;
